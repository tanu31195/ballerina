/*
*  Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing,
*  software distributed under the License is distributed on an
*  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
*  KIND, either express or implied.  See the License for the
*  specific language governing permissions and limitations
*  under the License.
*/
package org.wso2.ballerinalang.compiler.semantics.model.symbols;

import org.ballerinalang.model.symbols.StructSymbol;
import org.wso2.ballerinalang.compiler.semantics.model.types.BType;
import org.wso2.ballerinalang.compiler.util.Name;

import java.util.ArrayList;
import java.util.List;

import static org.wso2.ballerinalang.compiler.semantics.model.symbols.SymbolKinds.STRUCT;

/**
 * @since 0.94
 */
public class BStructSymbol extends BTypeSymbol implements StructSymbol {

    public List<BVarSymbol> fields;

    public BStructSymbol(Name name, BType type, BSymbol owner) {
        super(STRUCT, name, type, owner);
        fields = new ArrayList<>();
    }

    @Override
    public List<BVarSymbol> getFields() {
        return fields;
    }
}