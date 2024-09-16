# Udvikling af et chatprogram i Java med multithreading og sockets

I denne opgave skal I udvikle et chatprogram i Java, der anvender multithreading og socket-programmering til at facilitere realtidskommunikation mellem flere klienter og en server. Programmet skal kunne håndtere flere samtidige klientforbindelser, hvor hver klient kan sende og modtage beskeder. Desuden vil I implementere en simpel beskedprotokol for at strukturere kommunikationen mellem klient og server. Formålet med denne opgave er at anvende og uddybe jeres viden om netværkskommunikation (TCP og/eller UDP protokoller), grundlæggende og avanceret socket-programmering samt multithreading i Java. I vil også introducere yderligere funktioner som filoverførsel og emoji-support for at gøre applikationen mere brugervenlig og funktionel.

## Kravspecifikation

### Serverfunktionalitet

- Serveren skal kunne håndtere flere samtidige klientforbindelser ved hjælp af tråde, hvor hver klientforbindelse håndteres i en separat tråd.
- Serveren skal kunne modtage beskeder fra en klient og videresende dem til alle tilsluttede klienter (broadcast).
- Serveren skal kunne modtage beskeder fra en klient og videresende dem til en specifik tilsluttet klient (unicast).

### Klientfunktionalitet

- Klienten skal kunne forbinde til serveren.
- Klienten skal have en passende front-end.
- Klienten skal kunne sende beskeder til serveren og modtage beskeder fra serveren.

## Protokollen

Implementer en simpel beskedprotokol for at strukturere beskeder mellem klient og server. Protokollen skal indeholde klientens ID, tidsstempel og beskedindhold.

### Protokolstruktur

Protokollen består af flere elementer, som er adskilt af et bestemt separator-tegn (f.eks. |). Hver besked består af følgende komponenter:

- **Client ID**: Identifikator for klienten, der sender beskeden.  
  _Eksempel_: `client1`
- **Timestamp**: Angiver tidspunktet, hvor beskeden blev sendt. Formateret som `yyyy-MM-dd HH:mm`.  
  _Eksempel_: `2024-07-12 10:45:00`
- **Message Type**: Typen af besked såsom tekst, filoverførsel eller emoji.  
  _Eksempel_: `TEXT`
- **Content**: Selve beskedindholdet eller metadata afhængigt af beskedtypen.  
  _Eksempel for TEXT_: `Hello world!`

### Protokolformater

- **Tekstbesked**:  
  Format: `[CLIENT_ID]|[TIMESTAMP]|TEXT|[MESSAGE_CONTENT]`  
  Eksempel: `client1|2024-07-12 10:45:00|TEXT|Hello world!`
  
- **Filoverførsel**:  
  Format: `[CLIENT_ID]|[TIMESTAMP]|FILE_TRANSFER|[FILE_NAME]|[FILE_SIZE]`  
  Eksempel: `client1|2024-07-12 10:45:00|FILE_TRANSFER|doc.pdf|2048`
  
- **Emoji-besked**:  
  Format: `[CLIENT_ID]|[TIMESTAMP]|EMOJI|[EMOJI_CODE]`  
  Eksempel: `client1|2024-07-12 10:45:00|EMOJI|😊`

## Implementeringsdetaljer

### Beskedstrukturering

- Når en klient sender en besked, skal beskeden struktureres i det specificerede format, inden den sendes til serveren.

### Beskedbehandling på serveren

- Serveren modtager beskeden, parser den ved at splitte strengen ved hjælp af separator-tegnet (|), og identificerer hver del af beskeden.
- Serveren videresender beskeden til alle tilsluttede klienter, hvis det er en tekst- eller emoji-besked.
- Ved filoverførsel modtager serveren filens metadata og forbereder sig på at modtage filen i flere chunks.

### Beskedbehandling på klienten

- Klienten modtager beskeden fra serveren, parser den på samme måde som serveren, og håndterer den i henhold til beskedtypen.
- Tekst- og emoji-beskeder vises direkte i chatvinduet.
- Ved modtagelse af en filoverførsel begynder klienten at modtage og gemme filen lokalt.

## Tilføjelser

### Filoverførsel

- **Beskrivelse**: Muliggør overførsel af filer mellem klienter via serveren.
- **Implementering**: Klienten skal kunne anmode om at sende en fil. Serveren modtager filen og sender den videre til den relevante klient.
- **Protokol**: `[CLIENT_ID]|[TIMESTAMP]|FILE_TRANSFER|[FILE_NAME]|[FILE_SIZE]`  
  Eksempel: `client1|2024-07-12 10:45:00|FILE_TRANSFER|document.pdf|2048`

### Emoji-support

- **Beskrivelse**: Understøtter afsendelse og modtagelse af emojis i beskeder.
- **Implementering**: Emojis kan repræsenteres som tekstkoder og fortolkes af klientprogrammet for visning.
- **Protokol**: `[CLIENT_ID]|[TIMESTAMP]|[MESSAGE_CONTENT]`  
  Eksempel: `client1|2024-07-12 10:45:00|Hello 😊`

### Chatrum

- **Beskrivelse**: Understøtter oprettelse af separate chatrum, hvor brugere kan deltage og forlade rummet.
- **Implementering**: Serveren håndterer flere chatrum, og klienter kan sende kommandoer for at oprette, deltage i eller forlade chatrum.
- **Protokol**: `[CLIENT_ID]|[TIMESTAMP]|[COMMAND]|[ROOM_NAME]`  
  Eksempel: `client1|2024-07-12 10:45:00|JOIN_ROOM|room1`

### Brugerautentificering

- **Beskrivelse**: Tilføjer sikkerhed ved at kræve, at brugere logger ind med et brugernavn og en adgangskode.
- **Implementering**: Serveren validerer brugeroplysninger, før forbindelse accepteres.
- **Protokol**: `[CLIENT_ID]|[TIMESTAMP]|[LOGIN]|[USERNAME]|[PASSWORD]`  
  Eksempel: `client1|2024-07-12 10:45:00|LOGIN|user1|password123`

## Rammer

Ud over de faglige elementer, ligger der også et studiemæssigt sigte imod at I skal undersøge problemstillingen og mulige løsninger på egen hånd.

Opgaven løses over to uger, og hvor systemudvikling også deltager med noget undervisningstid. Programmet præsenteres i en kort præsentation (max. 10 minutter) for klassen fredag d. 27. september. Præsentationen skal indeholde en demonstration samt en forklaring af løsningen.

Gruppestørrelsen er 2-3 personer. I forhold til systemudvikling vil det nok være bedst at være to i grupperne.

**Happy coding**  
_Vibeke, M&Ms_
