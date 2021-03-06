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

package com.amazonaws.services.neptune;

import com.github.rvesse.airline.annotations.Alias;
import com.github.rvesse.airline.annotations.Cli;
import com.github.rvesse.airline.annotations.Parser;
import com.github.rvesse.airline.help.Help;

@Cli(name = "neptune-export.sh",
        description = "Export Neptune to CSV or JSON",
        defaultCommand = Help.class,
        commands = {
                ExportPropertyGraph.class,
                CreatePropertyGraphExportConfig.class,
                ExportPropertyGraphFromConfig.class,
                ExportPropertyGraphFromGremlinQueries.class,
                ExportRdfGraph.class,
                NeptuneExportSvc.class,
                Help.class},
        parserConfiguration = @Parser(aliases = {
                @Alias(name = "create-config",
                        arguments = {"create-pg-config"}),
                @Alias(name = "export",
                        arguments = {"export-pg"}),
                @Alias(name = "export-from-config",
                        arguments = {"export-pg-from-config"}),
                @Alias(name = "export-from-queries",
                        arguments = {"export-pg-from-queries"})
        }))
public class NeptuneExportCli {

    public static void main(String[] args) {

        com.github.rvesse.airline.Cli<Runnable> cli = new com.github.rvesse.airline.Cli<>(NeptuneExportCli.class);

        try {
            Runnable cmd = cli.parse(args);

            if (NeptuneExportBaseCommand.class.isAssignableFrom(cmd.getClass())) {
                ((NeptuneExportBaseCommand) cmd).applyLogLevel();
            }

            cmd.run();
        } catch (Exception e) {

            System.err.println(e.getMessage());
            System.err.println();

            Runnable cmd = cli.parse("help", args[0]);
            cmd.run();

            System.exit(-1);
        }
    }
}
