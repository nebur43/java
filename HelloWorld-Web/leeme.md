

mvn archetype:generate -DgroupId=es.prueba.ruben -DartifactId=HelloWorld-Web -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
Open pom.xml file and change packaging type to ‘pom’.
cd HelloWorld-Web
rm src
mvn archetype:generate -DgroupId=es.prueba.ruben -DartifactId=HelloWorldApp-war -DarchetypeArtifactId=maven-archetype-webapp -DinteractiveMode=false
mvn archetype:generate -DgroupId=es.prueba.ruben -DartifactId=HelloWorldApp-ear -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
cd HelloWorldApp-war\src\main
mkdir java
añadir al pom del war:
    <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>jstl</artifactId>
      <version>1.1.2</version>
    </dependency>
    <dependency>
      <groupId>javax.servlet.jsp</groupId>
      <artifactId>jsp-api</artifactId>
      <version>2.1</version>
    </dependency>
    <dependency>
      <groupId>com.sun.el</groupId>
      <artifactId>el-ri</artifactId>
      <version>1.0</version>
    </dependency>
    <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>javax.servlet-api</artifactId>
      <version>3.0.1</version>
    </dependency>						
