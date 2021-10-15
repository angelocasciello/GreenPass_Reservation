# GreenPass Reservation

## Informazioni generali
Questa repository contiene il codice del progetto d'esame realizzato per il corso di Software Architecture Design (corso di LM in ING-INF). <br>
Il progetto consiste in una Web Application chiamata "GreenPass Reservation", sviluppata mediante il framework Spring (Spring Boot Application).

## Scopo del progetto
L'applicazione si propone di risolvere una problematica nata a causa dell’esplosione della pandemia di SARS-CoV-2 e dalla conseguente introduzione del Green Pass: la certificazione europea che attesta l'avvenuta vaccinazione/guarigione da COVID-19 da parte di un cittadino. <br>
Come stabilito dal Decreto del Presidente del Consiglio dei ministri (DPCM) del 17 giugno 2021, il possesso di tale certificazione è divenuto obbligatorio per l’accesso al coperto di qualsiasi struttura, nonché ad eventi pubblici sul territorio nazionale. <br>
Il governo ha di conseguenza imposto ai gestori di locali ed eventi di verificare all’ingresso la validità dei Green Pass dei propri clienti (operazione che, attualmente, avviene in maniera manuale mediante l'app VerificaC19), al fine di consentirne l'accesso. <br>
Il progetto ha l'obiettivo di svincolare i gestori da tale mansione, consentendo ai clienti di effettuare la prenotazione ad un evento garantendo da remoto la validità del proprio Green Pass. <br>
Inoltre, si tiene a sottolineare la forte versatilità dell’applicazione, poiché, nonostante sia stata concepita per un determinato utilizzo (prenotazioni concerti), il suo campo d'applicazione può facilmente essere esteso a diversi altri contesti (prenotazioni ristoranti/bar/aule studio ecc.) <br>

## Tecnologie utilizzate
- Java Development Kit (JDK);
- Spring Tool Suite IDE (STS) release 4.11.0 come ambiente di sviluppo;
- Spring Web MVC come modello architetturale;
- Spring Data JPA e Hibernate per il Data Access Layer;
- Spring Security per l'autenticazione, il login e il logout;
- MySQL Community Server & MySQL Workbench 8.0 CE per la gestione e la memorizzazione dei dati;
- Thymeleaf come template engine;
- HTML5 e Bootstrap 4 per un'interfaccia utente reattiva;
- Apache Maven per la build automation;
- JUnit 5 e AssertJ per lo unit testing. <br>

## Funzionamento dell'applicazione
L'applicazione prevede due categorie di utilizzatori: clienti e gestori. <br>
I gestori possono creare degli eventi caratterizzati da svariati attributi (nome, data, orario, luogo, posti disponibili) e gestirli mediante la piattaforma. <br>
Ad ogni evento possono essere associate delle prenotazioni, che il gestore può consultare in un'apposita sezione della sua dashboard. <br>
I clienti possono prenotarsi agli eventi organizzati dai gestori, previo inserimento del loro Green Pass all'interno della piattaforma. <br>
L'inserimento del Green Pass (GP) avviene in un'apposita sezione, dove viene richiesto al cliente di inserire il codice identificativo del GP (UCI GP). <br>
L'inserimento andrà a buon fine solo se il cliente inserirà il codice del suo personale Green Pass, associato univocamente al suo codice fiscale (richiesto in fase di registrazione). <br>
La validità del codice e la corrispondenza con il codice fiscale del cliente viene verificata interrogando un database europeo, che memorizza tutte le informazioni riguardanti i Green Pass dei cittadini. <br>
IMPORTANTE: l'applicazione non contempla un effettivo collegamento con il reale Database Ministeriale dei Green Pass (come, invece, avviene nell'app ufficiale VerificaC19), a causa delle limitazioni imposte dalle normative; ma si limita a simularne una copia, prodotta manualmente dagli amministratori del sistema. Per comprendere come tale copia viene realizzata nella pratica, si invita a leggere l'ultimo paragrafo di questo documento. <br>
Una volta inserito il GP, il cliente potrà visualizzarlo all'interno di un'apposita sezione della sua dashboard. <br>
Un cliente potrà prenotarsi ad un evento solo se questo non è al completo (posti terminati) e il suo Green Pass (che ha una specifica data di scadenza) risulta valido almeno fino alla data dell'evento. <br>
Una volta effettuate delle prenotazioni, i clienti potranno visualizzarle e gestirle in un'apposita sezione della loro dashboard. <br>

## Deploy
L'applicazione non prevede un rilascio ufficiale al pubblico poiché, allo stato attuale, il Garante per la protezione dei dati personali ha autorizzato unicamente l'app “VerificaC19” come software per la validazione dei Green Pass dei cittadini. <br>
Tuttavia è possibile, per scopi dimostrativi, effettuare il deploy dell'applicazione direttamente sulla macchina dell' host (facendo girare l’app in locale). <br>
Di seguito saranno illustrati, con dovizia di particolari, tutti i passaggi necessari. <br>
### Passaggi preliminari
Scaricare e installare i seguenti software dai rispettivi siti ufficiali, se non li si ha già installati sulla propria macchina:
- Java Development Kit (JDK).
- Spring Tool Suite (release 4.11.0 o successiva).
- Scaricare e installare MySQL e MySQL Workbench (versione 8.0 o successiva).
### Creazione del DB
- Aprire MySQL Workbench e creare una connessione avente la seguente configurazione: <br>
  Connection Name: localhost <br>
  Connection Method: Standard (TCP/IP) <br>
  Hostname: 127.0.0.1 <br>
  Port: 3306 <br>
  Username: root <br>
  Password: projectsad12345 <br>
- Aprire la connessione creata e creare un nuovo schema denominato "greenpass_reservation_db".
- Scaricare l'archivio .zip del progetto da GitHub.
- Aprire Spring Tool e andare su: <br>
  File -> Open Projects from File System... -> cliccare su "Archive" -> selezionare l'archivio .zip del progetto -> Finish. <br>
- Dal package explorer, spostarsi in "src/main/resources" e successivamente sul file "application.properties".
- Modificare "spring.jpa.hibernate.ddl-auto=none" in "spring.jpa.hibernate.ddl-auto=create" -> questa operazione dice a Hibernate che si intende creare le tabelle.
- Dal package explorer spostarsi in "src/test/java/" e poi espandere "net.code.java".
- Selezionare la classe di test "ClienteRepositoryTests.java", cliccare con il tasto destro su di essa e successivamente selezionare "Run As" -> "JUnit test" (volendo è possibile    modificare i dati del cliente editando la classe) -> questa operazione genera tutte le tabelle vuote nel db, eccetto la tabella "clienti", che conterrà unicamente la riga relativa al cliente definito nella classe di test.
- Ritornare su "application.properties" e modificare nuovamente "spring.jpa.hibernate.ddl-auto=create" in "spring.jpa.hibernate.ddl-auto=none" -> questa operazione fa si che al prossimo inserimento Hibernate non ricrei da zero le tabelle.
### Avvio applicazione
- Tra i package del progetto, spostarsi su "src/main/java" ed espandere "net.code.java".
- Selezionare la classe "GreenPass_Reservation.java", cliccare con il tasto destro su di essa e selezionare "Run As" -> "Spring Boot App".
- Collegarsi a http://localhost:8080/ per utilizzare l'applicazione (sarà già possibile effettuare il login con i dati del cliente definito nella classe di test).

## Simulazione del DB Ministeriale
L’applicazione prevede un interfacciamento con il Database Ministeriale (contenente i codici GP in corso di validità di tutti i cittadini) per effettuare il controllo “incrociato” all’atto dell’inserimento del GP nella piattaforma da parte di un cliente. <br>
Per i motivi elencati in precedenza questo database è stato simulato in locale mediante la tabella “green_pass” nel database MySQL dell’applicazione. <br>
La tabella in questione, di conseguenza, dovrà essere riempita manualmente dall’amministratore di sistema con tutti i Green Pass (con i vari attributi) da usare nelle demo. <br>
Questa operazione può essere effettuata attraverso i seguenti passaggi:
- Aprire il progetto su Spring Tool.
- Dal package explorer, spostarsi su "src/test/java/", espandere "net.code.java" ed aprire la classe "GreenPassRepositoryTests.java".
- All'interno della classe, modificare la funzione di test inserendo i dati associati al Green Pass del cittadino che si intende memorizzare nel DB.
- Selezionare la classe dal package explorer, cliccare su di essa con il tasto destro e selezionare "Run As" -> "JUnit test".
- Alternativamente, è possibile effettuare l'inserimento anche utilizzando l’interfaccia utente di MySQL Workbench, mediante l'esecuzione di una INSERT sulla tabella.

## Autori
- [Angelo Casciello](https://github.com/angelocasciello)
- [Alfonso Ferrara](https://github.com/alfonsoferrara99)
- [Michael Carannante](https://github.com/miky98)
- [Umberto Pier Rosario Caturano](https://github.com/umberpiero)
