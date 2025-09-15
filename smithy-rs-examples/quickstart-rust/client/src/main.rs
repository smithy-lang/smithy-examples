/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

use clap::Parser;
use coffeeshop_client_sdk::types::{CoffeeType, OrderStatus};
use std::time::Duration;
use tracing_subscriber::{EnvFilter, prelude::*};

pub const DEFAULT_ADDRESS: &str = "127.0.0.1";
pub const DEFAULT_PORT: u16 = 8888;

#[derive(Parser, Debug)]
#[clap(author, version, about, long_about = None)]
struct Args {
    /// Hyper server bind address.
    #[clap(short, long, action, default_value = DEFAULT_ADDRESS)]
    address: String,
    /// Hyper server bind port.
    #[clap(short, long, action, default_value_t = DEFAULT_PORT)]
    port: u16,
}

/// Setup `tracing::subscriber` to read the log level from RUST_LOG environment variable.
pub fn setup_tracing() {
    let format = tracing_subscriber::fmt::layer();
    let filter = EnvFilter::try_from_default_env()
        .or_else(|_| EnvFilter::try_new("info"))
        .unwrap();
    tracing_subscriber::registry()
        .with(format)
        .with(filter)
        .init();
}

#[tokio::main]
async fn main() {
    let args = Args::parse();
    setup_tracing();
    let service_endpoint = format!("http://{}:{}", args.address, args.port);
    tracing::info!("connecting to {service_endpoint}");

    let config = coffeeshop_client_sdk::Config::builder()
        .endpoint_url(service_endpoint)
        .build();
    let client = coffeeshop_client_sdk::Client::from_conf(config);

    let create_resp = client
        .create_order()
        .coffee_type(CoffeeType::Latte)
        .send()
        .await
        .expect("failed to create order");

    println!(
        "Order created for a {} with id {}",
        create_resp.coffee_type(),
        create_resp.id()
    );

    loop {
        let order = client
            .get_order()
            .id(create_resp.id())
            .send()
            .await
            .expect("failed to get order");

        match order.status {
            OrderStatus::Completed => {
                println!(
                    "Order {} for a {} is ready!",
                    order.id(),
                    order.coffee_type()
                );
                break;
            }
            OrderStatus::InProgress => {
                println!("Waiting for order {} to complete...", order.id());
                tokio::time::sleep(Duration::from_secs(2)).await;
            }
            _ => panic!("unknown order status"),
        }
    }
}
