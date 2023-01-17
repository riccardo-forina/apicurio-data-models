/*
 * Copyright 2019 Red Hat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.apicurio.datamodels.validation.rules.mutex;

import java.util.List;

import io.apicurio.datamodels.models.Node;
import io.apicurio.datamodels.models.Operation;
import io.apicurio.datamodels.models.openapi.OpenApiParameter;
import io.apicurio.datamodels.models.openapi.OpenApiPathItem;
import io.apicurio.datamodels.util.NodeUtil;
import io.apicurio.datamodels.validation.ValidationRule;
import io.apicurio.datamodels.validation.ValidationRuleMetaData;

/**
 * Implements the Body and Form Data Mutual Exclusivity Rule.
 * @author eric.wittmann@gmail.com
 */
public class OasBodyAndFormDataMutualExclusivityRule extends ValidationRule {

    /**
     * Constructor.
     * @param ruleInfo
     */
    public OasBodyAndFormDataMutualExclusivityRule(ValidationRuleMetaData ruleInfo) {
        super(ruleInfo);
    }

    private void visitParameterParent(Node paramParent) {
        @SuppressWarnings("unchecked")
        List<OpenApiParameter> parameters = (List<OpenApiParameter>) NodeUtil.getNodeProperty(paramParent, "parameters");
        if (hasValue(parameters)) {
            boolean hasBodyParam = false;
            boolean hasFormDataParam = false;
            for (OpenApiParameter param : parameters) {
                if (equals(param.getIn(), "body")) {
                    hasBodyParam = true;
                }
                if (equals(param.getIn(), "formData")) {
                    hasFormDataParam = true;
                }
            }
            this.reportIf(hasBodyParam && hasFormDataParam, paramParent, "in", map());
        }
    }

    /**
     * @see io.apicurio.datamodels.models.visitors.CombinedVisitorAdapter#visitOperation(io.apicurio.datamodels.models.Operation)
     */
    @Override
    public void visitOperation(Operation node) {
        visitParameterParent(node);
    }

    /**
     * @see io.apicurio.datamodels.models.visitors.CombinedVisitorAdapter#visitPathItem(io.apicurio.datamodels.models.openapi.OpenApiPathItem)
     */
    @Override
    public void visitPathItem(OpenApiPathItem node) {
        visitParameterParent(node);
    }

}
