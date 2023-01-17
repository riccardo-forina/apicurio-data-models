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

package io.apicurio.datamodels.validation.rules.invalid.format;

import io.apicurio.datamodels.models.Operation;
import io.apicurio.datamodels.models.openapi.v20.OpenApi20Operation;
import io.apicurio.datamodels.validation.ValidationRule;
import io.apicurio.datamodels.validation.ValidationRuleMetaData;

/**
 * Implements the Invalid Operation Consumes Rule
 * @author eric.wittmann@gmail.com
 */
public class OasInvalidOperationConsumesRule extends ValidationRule {

    /**
     * Constructor.
     * @param ruleInfo
     */
    public OasInvalidOperationConsumesRule(ValidationRuleMetaData ruleInfo) {
        super(ruleInfo);
    }

    /**
     * @see io.apicurio.datamodels.models.visitors.CombinedVisitorAdapter#visitOperation(io.apicurio.datamodels.models.Operation)
     */
    @Override
    public void visitOperation(Operation node) {
        OpenApi20Operation op = (OpenApi20Operation) node;
        if (hasValue(op.getConsumes())) {
            this.reportIfInvalid(isValidMimeType(op.getConsumes()), node, "consumes", map());
        }
    }

}
