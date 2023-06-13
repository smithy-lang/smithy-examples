/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: MIT-0
 */

package io.smithy.examples.traits;

import java.util.ArrayList;
import java.util.List;
import software.amazon.smithy.model.node.Node;
import software.amazon.smithy.model.node.NodeMapper;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.traits.AbstractTrait;
import software.amazon.smithy.model.traits.AbstractTraitBuilder;
import software.amazon.smithy.model.traits.Trait;
import software.amazon.smithy.utils.ListUtils;
import software.amazon.smithy.utils.SmithyBuilder;
import software.amazon.smithy.utils.ToSmithyBuilder;

public final class ResourceMetadataTrait extends AbstractTrait
    implements ToSmithyBuilder<ResourceMetadataTrait> {

    public static final ShapeId ID = ShapeId.from("io.smithy.example#resourceMetadata");

    private final String description;
    private final String type;
    private final List<ShapeId> associatedStructures;

    private ResourceMetadataTrait(Builder builder) {
        super(ID, builder.getSourceLocation());
        description = builder.description;
        type = builder.type;
        associatedStructures = ListUtils.copyOf(builder.associatedStructures);
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public List<ShapeId> getAssociatedStructures() {
        return ListUtils.copyOf(associatedStructures);
    }


    public static final class Provider extends AbstractTrait.Provider {
        public Provider() {
            super(ID);
        }

        @Override
        public Trait createTrait(ShapeId target, Node value) {
            ResourceMetadataTrait result = new NodeMapper().deserialize(value, ResourceMetadataTrait.class);
            result.setNodeCache(value);
            return result;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    protected Node createNode() {
        NodeMapper mapper = new NodeMapper();
        mapper.disableToNodeForClass(ResourceMetadataTrait.class);
        mapper.setOmitEmptyValues(true);
        return mapper.serialize(this).expectObjectNode();
    }

    @Override
    public SmithyBuilder<ResourceMetadataTrait> toBuilder() {
        return builder().sourceLocation(getSourceLocation())
            .description(description)
            .type(type)
            .associatedStructures(associatedStructures);
    }

    public static final class Builder extends AbstractTraitBuilder<ResourceMetadataTrait, Builder> {
        private String description;
        private String type;
        private final List<ShapeId> associatedStructures = new ArrayList<>();

        private Builder() {}

        @Override
        public ResourceMetadataTrait build() {
            return new ResourceMetadataTrait(this);
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder addAssociatedStructure(ShapeId associatedStructure) {
            this.associatedStructures.add(associatedStructure);
            return this;
        }

        public Builder associatedStructures(List<ShapeId> associatedStructures) {
            this.associatedStructures.clear();
            this.associatedStructures.addAll(associatedStructures);
            return this;
        }
    }
}
