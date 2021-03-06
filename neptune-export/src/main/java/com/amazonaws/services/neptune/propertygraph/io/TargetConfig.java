/*
Copyright 2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
Licensed under the Apache License, Version 2.0 (the "License").
You may not use this file except in compliance with the License.
A copy of the License is located at
    http://www.apache.org/licenses/LICENSE-2.0
or in the "license" file accompanying this file. This file is distributed
on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
express or implied. See the License for the specific language governing
permissions and limitations under the License.
*/

package com.amazonaws.services.neptune.propertygraph.io;

import com.amazonaws.services.neptune.io.Directories;
import com.amazonaws.services.neptune.propertygraph.metadata.PropertyTypeInfo;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.Map;

public class TargetConfig {

    private final Directories directories;
    private final Format format;
    private final Output output;
    private final boolean includeTypeDefinitions;

    public TargetConfig(Directories directories, Format format, Output output, boolean includeTypeDefinitions) {
        this.directories = directories;
        this.format = format;
        this.output = output;
        this.includeTypeDefinitions = includeTypeDefinitions;
    }

    public String formatDescription() {
        return format.description();
    }

    public String outputDescription() {
        return output.name();
    }

    public Printer createPrintWriterForQueries(String name, int index, Map<Object, PropertyTypeInfo> metadata) throws IOException {
        Path directory = directories.resultsDirectory().resolve(name);
        java.nio.file.Path filePath = directories.createFilePath(directory, name, index, format);
        PrintWriter printWriter =  output.createPrintWriter(filePath);

        return format.createPrinter(printWriter, metadata, includeTypeDefinitions);
    }

    public Printer createPrinterForEdges(String name, int index, Map<Object, PropertyTypeInfo> metadata) throws IOException {
        java.nio.file.Path filePath = directories.createFilePath(directories.edgesDirectory(), name, index, format);
        PrintWriter printWriter = output.createPrintWriter(filePath);

        return format.createPrinter(printWriter, metadata, includeTypeDefinitions);
    }

    public Printer createPrinterForNodes(String name, int index, Map<Object, PropertyTypeInfo> metadata) throws IOException {
        java.nio.file.Path filePath = directories.createFilePath(directories.nodesDirectory(), name, index, format);
        PrintWriter printWriter = output.createPrintWriter(filePath);

        return format.createPrinter(printWriter, metadata, includeTypeDefinitions);
    }
}
