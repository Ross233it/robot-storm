

# INTRO:
Questa implementazione della simulazione si basa su tre elementi principali:

1 - Hardware: ovvero oggetti programmabili come i Robot sono elementi autonomi,
con una propria memoria e un sistema interno di esecuzione delle istruzioni.

2 - Software: componenti che consentono l'implementazioni delle singole
istruzioni elementari e ricorsive, e le modalità per interpretare il programma inteso come
sequenza di istruzioni con una sintassi precisa.

3 - l'ambiente, che include sia lo spazio generale in cui la simulazione si 
svolge sia gli elementi statici in esso contenuti.

L'applicazione è stata sviluppata seocondo il modello Model Control View
# Responsabilità ed Astrazioni:

### Model - Environment

#### A - Interfaccia Shape:
L'interfaccia astrae il concetto di forma intesa come oggetto geometrico collocato nello spazio della simulazione.
La forma ha un suo stato rappresentato da una label, delle dimensioni specifiche e una posizione all'interno di uno spazio.

#### A1 - Classe StaticCircle
Implementa Shape - Ha la responsabilità di rappresentare la forma statica circolare che può essere disposta nello spazio 2D.
E' in grado di fornire i propri dati costruttivi e geometrici.

#### A1 - Classe StaticRectangle
Implementa Shape - Ha la responsabilità di rappresentare la forma statica rettangolare che può essere disposta nello spazio 2D.
E' in grado di fornire i propri dati costruttivi e geometrici.

#### B - Interfaccia Environment:
L'interfaccia astrae il concetto d'ambiente illimitato all'interno del quale sono posizionati
oggetti statici e nel quale svolgono le loro attività oggetti mobili e programmabili.
Le classi che implementano questa interfaccia costruiscono l'ambiente dove si svolge la
simulazione.

#### B1 - Classe BidimensionalSpace
Implementa l'interfaccia Environment ed ha la responsabilità di registrare lo stato 
di un ambiente bidimensionale  in un dato momento della simulazione. 
Spazio sul quale si muovono oggetti programmabili e risiedono forme statiche di cui può 
fornire informazioni.

### Model - Hardware

#### C - Interfaccia ProgrammableObject:
Astrae il concetto di 'oggetto programmabile', ovvero
un elemento fisico inserito nell'ambiente, che possiede uno stato definito in una label ,
una posizione e la capacità modificarle.

#### C1 - Classe Robot
Implementa l'interfaccia ProgrammableObject -
Robot ed ha la responsabilità di rappresentare l'oggetto
di tipo robot caratterizzato da label , position  e direction in conformità con l'interfaccia
e in aggiunta da un dispositivo di memoria e da un software per l'esecuzione delle istruzioni.

#### D - Interfaccia Memory:
Astrae il concetto di memoria secondaria dove è possibile immagazzinare dati

#### D1 - Classe RobotMemory
Implementa l'interfaccia Memory ed ha la responsabilità di memorizzare 
gli stati di un robot durante l'evoluzione della simulazione.

#### Classe Record RobotState
Rappresenta l'unità base per la memorizzazione degli stati dei robot.

### Model - Software

#### E - Interfaccia ProgramExecutor:
Astrae il concetto di interprete di comandi ovvero di un dispositivo in grado
di ricevere una istruzione e lanciarne l'esecuzione.

#### E1 - Classe RobotProgramExecutor
Implementa robot executor - ha la responsabilità di recepire un comando e 
lanciare la conseguente azione del robot.

#### Classe RobotLanguageAtomicConstructs
Ha la responsabilità di implementare i comandi sequenziali del linguaggio di programmazione;

#### Classe RobotLanguageLoopConstructs
Ha la responsabilità di implementare il flusso di esecuzione del programma in 
relazione ai comandi del linguaggio di programmazione.

#### Classe ProgramCommand
Rappresenta il comando base del linguaggio di programmazione. La responsabilità di 
questa classe è quella di formattare e standardizzare i comandi del linguaggio di programmazione
ed i loro parametri in un oggetto comprensibile all'esecutore.


## CONTROLLER:
Ha la responsabilità di assemblare tutti gli elementi necessari per 
l'esecuzione della simulazione. Per fare questo interagisce con il Model
e restituisce informazioni alla view.


## WIEW
### Setup view
Consente di impostare i paremtri generali della simulazione:
- numero dei robots;
- dimensione dell'unita di tempo (1000 = 1 secondo);
- durata della simulazione in secondi;
- file contenente il programma;
- file contenente le forme da inserire nell'ambiente;

### Simulation view
Consente di visualizzare il comportamento dei robot durante la simulazione:
- Pulsante Clean: ripristina la simulazione allo stato iniziale;
- Pulsante StepForward: esegue uno step in avanti della simulazione;
- Pulsante Play esegue la simulazione come una successione di operazioni scandite da timeUnit;
- Pulsante ZoomIn ingrandisce la visione sull'ambiente della simulazione
- Pulsante ZoomOut allontana la visione dall'ambiente di simulaizone;


