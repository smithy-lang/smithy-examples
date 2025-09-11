/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

use coffeeshop_server_sdk::{
    error, input,
    model::{self, CoffeeItem, CoffeeType, OrderStatus},
    output::{self, CreateOrderOutput},
    server::Extension,
};
use std::{
    collections::HashMap,
    sync::{Arc, Mutex},
    time::Duration,
};

/// CoffeeShop service shared state.
///
/// Some applications may want to manage state between handlers. Imagine having a database connection pool
/// that can be shared between different handlers and operation implementations.
/// State management can be expressed in a struct where the attributes hold the shared entities.
///
/// **NOTE: It is up to the implementation of the state structure to handle concurrency by protecting**
/// **its attributes using synchronization mechanisms.**
///
/// The framework stores the `Arc<T>` inside an `http::Extensions` and conveniently passes it to
/// the operation's implementation, making it able to handle operations with two different async signatures:
/// * `FnOnce(InputType) -> Future<OutputType>`
/// * `FnOnce(InputType, Extension<Arc<T>>) -> Future<OutputType>`
///
/// Wrapping the service with a [`tower::Layer`] will allow to have operations' signatures with and without shared state:
///
/// ```compile_fail
/// use std::sync::Arc;
/// use aws_smithy_http_server::{AddExtensionLayer, Extension, Router};
/// use tower::ServiceBuilder;
/// use tokio::sync::RwLock;
///
/// // Shared state,
/// #[derive(Debug, State)]
/// pub struct State {
///     pub count: RwLock<u64>
/// }
///
/// // Operation implementation with shared state.
/// async fn operation_with_state(input: Input, state: Extension<Arc<State>>) -> Output {
///     let mut count = state.0.write().await;
///     *count += 1;
///     Ok(Output::new())
/// }
///
/// // Operation implementation without shared state.
/// async fn operation_without_state(input: Input) -> Output {
///     Ok(Output::new())
/// }
///
/// let app: Router = OperationRegistryBuilder::default()
///     .operation_with_state(operation_with_state)
///     .operation_without_state(operation_without_state)
///     .build()
///     .unwrap()
///     .into();
/// let shared_state = Arc::new(State::default());
/// let app = app.layer(ServiceBuilder::new().layer(AddExtensionLayer::new(shared_state)));
/// let server = hyper::Server::bind(&"0.0.0.0:13734".parse().unwrap()).serve(app.into_make_service());
/// ...
/// ```
///
/// Without the middleware layer, the framework will require operations' signatures without
/// the shared state.
///
/// [`middleware`]: [`aws_smithy_http_server::AddExtensionLayer`]
#[derive(Debug, Default)]
pub struct State {
    // keep track of orders in-memory
    orders: Arc<Mutex<HashMap<model::Uuid, CreateOrderOutput>>>,
}

// Handler that returns the requested order ID if found
pub async fn get_order(
    input: input::GetOrderInput,
    state: Extension<Arc<State>>,
) -> Result<output::GetOrderOutput, error::GetOrderError> {
    let orders = state.orders.lock().unwrap();
    match orders.get(input.id()) {
        Some(order) => Ok(output::GetOrderOutput {
            id: order.id.clone(),
            coffee_type: order.coffee_type.clone(),
            status: order.status.clone(),
        }),
        None => {
            tracing::warn!("order {} not found", input.id());
            Err(error::OrderNotFound {
                order_id: Some(input.id().clone()),
                message: Some("Order not found".to_string()),
            }
            .into())
        }
    }
}

// Handler to create a new coffee order
pub async fn create_order(
    input: input::CreateOrderInput,
    state: Extension<Arc<State>>,
) -> Result<output::CreateOrderOutput, error::CreateOrderError> {
    let id = uuid::Uuid::new_v4().to_string();
    tracing::info!("created order {id} for {:?}", input.coffee_type());
    let order = output::CreateOrderOutput {
        id: model::Uuid::try_from(id).expect("valid uuid"),
        coffee_type: input.coffee_type,
        status: OrderStatus::InProgress,
    };

    let mut orders = state.orders.lock().unwrap();
    orders.insert(order.id.clone(), order.clone());
    let state = state.clone();
    let in_progress = order.id.clone();
    tokio::task::spawn(async move {
        tokio::time::sleep(Duration::from_secs(5)).await;
        let mut orders = state.orders.lock().unwrap();
        tracing::info!("Order {:?} complete", in_progress);
        orders
            .entry(in_progress)
            .and_modify(|o| o.status = OrderStatus::Completed);
    });
    Ok(order)
}

/// Retrieves the coffe shop's menu
pub async fn get_menu(
    _input: input::GetMenuInput,
    _state: Extension<Arc<State>>,
) -> Result<output::GetMenuOutput, std::convert::Infallible> {
    let items = vec![
        CoffeeItem::builder()
            .r#type(CoffeeType::Drip)
            .description(
                "A clean-bodied, rounder, and more simplistic flavour profile. Often praised for mellow and less intense notes. Far less concentrated than espresso.".to_string())
            .build()
            .unwrap(),
        CoffeeItem::builder()
            .r#type(CoffeeType::PourOver)
            .description(
                "Similar to drip coffee, but with a process that brings out more subtle nuances in flavor. More concentrated than drip, but less than espresso.".to_string())
            .build()
            .unwrap(),
        CoffeeItem::builder()
            .r#type(CoffeeType::Latte)
            .description(
                "A creamier, milk-based drink made with espresso. A subtle coffee taste, with smooth texture. High milk-to-coffee ratio.".to_string())
            .build()
            .unwrap(),
        CoffeeItem::builder()
            .r#type(CoffeeType::Espresso)
            .description(
                "A highly concentrated form of coffee, brewed under high pressure. Syrupy, thick liquid in a small serving size. Full bodied and intensely aromatic.".to_string())
            .build()
            .unwrap(),
    ];

    let menu = output::GetMenuOutput { items: Some(items) };
    Ok(menu)
}
