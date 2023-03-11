# Toetsvragen

N.a.v. maken ChatJava applicatie.

## Exceptions

1. Gegeven het volgende stukje Java code:

```java
    private static HelloRmiInterface lookupHelloProxy(String host) {
        Registry registry = LocateRegistry.getRegistry(host);
        HelloRmiInterface proxy = (HelloRmiInterface) registry.lookup("hi-wereld");
        return proxy;
    }

```

Bij compileren hiervan geeft de Java compiler de volgende error:

```console
HelloRmiClient.java:23: error: unreported exception RemoteException; must be caught or declared to be thrown
```
  * Java maakt onderscheid tussen checked en unchecked exceptions. Is de genoemde `RemoteException` een checked exception of een unchecked exception?
  * Van de twee in de compilerfout genoemde oplossingsrichtingen voeg je bij de een iets toe in de methode signature en bij de andere in de methode. Beschrijf wat we met de 'methode signature' bedoelen. (3pnt)
  * Geef de uitwerking van beide code aanpassingen (2 pnt per goede uitwerking). Voor een aanpassing geeft collega aan volgende code toe te gebruiken voor loggen van opgetreden fout
```java
    logger.log("RemoteException bij lookup RMI registry! Error: " + e.toString());
    e.printStackTrace();
```
  * Een collega geeft aan dat je bij het schrijven van code geen fouten moet inslikken, maar deze beter zo snel mogelijk moet laten klappen. Welke van de twee zou jij dan kiezen? Noem hiervan een voordeel, en een nadeel.
  * Je projectleider geeft aan dat hij het belangrijk vindt om robuuste code te schrijven en om low level exceptions altijd in te pakken in eigen custom exceptions. Welke oplossing zou je dan kiezen. Noem van deze oplosingsrichting een voordeel en een nadeel.


## Antwoord

Aanpassen
```java
    private static HelloRmiInterface getHelloProxy(String host) throws RemoteException {
        ...
    }
```

Het voordeel van het doorgooien van de fout (en `throws RemoteException`) en 'laten klappen' is dat je direct foutmelding krijgt, en geen fouten inslikt of enkel logt. Maar dat je aanroepende code meteen moet fixen.

```java
    private static HelloRmiInterface getHelloProxy(String host) {
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            HelloRmiInterface proxy = (HelloRmiInterface) registry.lookup(HelloRmiInterface.NAAM);
            return proxy;
       } catch (RemoteException e) {
            logger.log("RemoteException bij opzoeken : " + e.toString());
            e.printStackTrace();
        }
```
