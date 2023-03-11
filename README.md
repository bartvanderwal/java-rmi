# RPC & Java RMI - Overzicht en concrete code

Welkom in deze repository, met code en een [achtergrond artikel over **RMI**\(blog.md): *Remote Method Invocation* in Java. Dit is een implementatie van het concept **RPC**: *Remote Procedure Call*.

Maar zoals hoort in een README nu eerst een 'how to run'. Het artikel/theorie staat verder naar beneden.

## How to run? (Development environment)

Download als zip, of clone deze repository. Of run in GitHub als GitHub code spaces.
Run onderstaande commando's in de terminal.
Uitleg staat hierbij. Evt. te refactoren naar startup script, en/of in een Java enabled Docker container.

### Terminal

De gecompileerde classes gaan naar `target` folder, alvast de Mavan standaard.

Voor projecten met meer dan een file, en geneste structuur/packages moet je `-sourcepath` parameter gebruiken.
Anders krijg je veel 'cannot find symbol` errors.
Bron: https://stackoverflow.com/questions/13407983/javac-cannot-find-symbol-error-with-command-line

```bash
# Starten/aanmaken RMR registry op host computer (vereist voor werken RMI)
# Deprecated: De server programma maakt nu de 'RMI Registry' aan.
# rmiregistry # port 1099 default

# Genereren stubs en skeletons voor gebruik client en server voor onderlinge communicatie
# Deprecated: De stubs en skeleton worden op runtime dynamisch gegenereerd wanneer nodig,
# Maar toch vermeldenswaardig vanwege parallel voor noodzaak generatie/interface definitie in alternatieven van RMI
# Zoals moderne gRPC (native), standaard REST API (Swagger, 3rd party) of old skool SOAP (wsdl e.d.)
# rmic -classpath HelloRmiInterface

# Compileer de code voor de Server app.
javac -d target -sourcepath src src/chatjava/ChatServerApp.java

# Compileer de code voor de Client app.
javac -d target -sourcepath src src/chatjava/ChatClientApp.java

# Run eerst de server (1e proces met eigen JVM).
java -cp target chatjava/ChatServerApp

# Run dan de client (2e proces met ook eigen JVM)
# Omdat server nog draait, moet je 2e prompt openen, bv. 2e 'tabblad'.
java -cp target chatjava/ChatClientApp
```

Voor zelf runnen van server: kopieer config bestand `src/resources/server.properties.template` naar `src/resources/server.properties` en vul API key in met [eigen geldige waarde (zo op te vragen als je openAI account hebt)](https://platform.openai.com/account/api-keys)).

### Stoppen rmiregistry handmatig

Om de rmiregistry handmatig te stoppen, kun je het volgende doen.

macOS
```bash
lsof -i :1099
kill <returnedPid>
```

### Genereren plantuml diagrammen

Voor het tonen/genereren van de PlantUml diagrammen in VS Code run [plantuml  server in docker container](https://hub.docker.com/r/plantuml/plantuml-server) lokaal op *poort* `8081` met dit commando:

```bash
docker run -d -p 8081:8080 plantuml/plantuml-server:tomcat
```

# Theorie, achtergrond

Lees het [achtergrondartikel](blog.md) dat ik schreef.

Mogelijke deelvragen:

- Wat is RPC?
- Hoe maak je een concrete implementatie van RPC in Java?
- Welke varianten zijn er, en wat is huidige state-of-the-art of trend qua RPC?