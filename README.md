itcrowd-domain-findbugs
=======================

FindBugs checks for domain model annotations consistency.

Here is how you need to configure it:

    <build>
        <plugins>
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>findbugs-maven-plugin</artifactId>
                <version>2.5.2</version>
                <configuration>
                    <plugins>
                        <plugin>
                            <groupId>pl.itcrowd.findbugs.domain</groupId>
                            <artifactId>itcrowd-domain-findbugs</artifactId>
                            <version>1.0.0-SNAPSHOT</version>
                        </plugin>
                    </plugins>
                </configuration>
            </plugin>
        </plugins>
    </build>