Kemi zhvilluar një aplikacion mobil për Android (në Java), i shoqëruar me një backend të fortë, që thjeshton ndjeshëm menaxhimin e takimeve, diagnozave dhe komunikimin mes mjekëve dhe pacientëve.

Aplikacioni ka dy role kryesore:
Pacienti: Mund të caktojë takime me mjekun, të shohë diagnozat e tij, të ndjekë terminet e bëra dhe të komunikojë direkt me mjekun. Gjithashtu, mund të menaxhojë dhe përditësojë të dhënat personale.
Doktori: Ka mundësinë të shkruajë diagnoza për pacientët, të menaxhojë terminet (duke ndryshuar statuset), të shohë diagnozat e tij, të komunikojë me pacientët dhe të përditësojë të dhënat personale.

Detajet Teknike të Aplikacionit Mobil:
Për pjesën mobile, kemi përdorur arkitekturën MVVM për çdo modul, duke e bërë kodin më të pastër dhe të menaxhueshëm. 
Sistemi i njoftimeve (notifications) është i integruar mirë, duke përdorur backend-in tonë dhe Firebase Cloud Messaging (FCM). Kjo siguron që pacientët të njoftohen menjëherë për diagnoza të reja, ndryshime në takime apo mesazhe të reja, ndërsa mjekët marrin njoftime për mesazhet e pacientëve.
Përdorim i Jetpack komponentëve si LiveData, ViewModel,për modularitet dhe mirëmbajtje më të mirë.
Për të mos u lodhur përdoruesi me hyrje të vazhdueshme, kemi menaxhuar sesionin e login-it duke përdorur refresh tokens, kështu që nuk kërkohet ri-login derisa token-i të skadojë ose përdoruesi të dalë vetë.

Arkitektura dhe Teknologjitë e Backend-it:
Backend-i është ndërtuar me një arkitekturë microservice, duke përdorur Spring Boot. 
Për bazat e të dhënave, kemi kombinuar SQL Server për të dhënat relacionale dhe MongoDB për ato jo-relacionale. 
Komunikimi mes shërbimeve bëhet kryesisht përmes event-driven architecture. 
Si pikë hyrjeje për të gjitha shërbimet, kemi përdorur Spring Cloud Gateway me Load Balancer. 
E gjithë zgjidhja është e containerizuar me Docker, duke e bërë vendosjen dhe menaxhimin shumë më të lehtë. 
Për komunikimin në kohë reale mes mjekut dhe pacientit, kemi implementuar WebSockets në shërbimin përkatës.

![Welcome](https://github.com/user-attachments/assets/caf81d19-41bf-45c6-8e07-7067f77c4765)
![Login](https://github.com/user-attachments/assets/42cfba95-c0a7-4caf-91b0-d326bfc1b645)
![Register](https://github.com/user-attachments/assets/4f7c4484-32eb-460d-b996-05dd73f84f87)
![User Make an Appointment](https://github.com/user-attachments/assets/da006256-e7bf-4263-a6f6-04c601a4c1af)
![User My Appointments](https://github.com/user-attachments/assets/68df3a02-00cf-49be-878a-f7756a989862)
![User Message](https://github.com/user-attachments/assets/4dbadebc-8b12-48ad-86f4-59898c2a8e95)
![User Chat](https://github.com/user-attachments/assets/43b9c557-7753-44fc-837e-11d9f8006437)
![User My Diagnoses](https://github.com/user-attachments/assets/4cd88f56-03e6-40e3-a637-994d5cec3c66)
![Doctor Create Diagnoses](https://github.com/user-attachments/assets/c9b3872f-5cc8-49f7-9c3a-7c803bff2c04)
![Doctor My Appointments](https://github.com/user-attachments/assets/72d704c6-a719-41ac-8058-532a18f6fb37)
![Doctor Chat](https://github.com/user-attachments/assets/864110f5-67db-4a7c-81c2-8b6a7e34bf95)
![Doctor Message](https://github.com/user-attachments/assets/d66b313b-68da-466a-97a7-58139dfe535d)
![Doctor My Diagnoses](https://github.com/user-attachments/assets/d827482f-7893-46f0-bdc1-a832b3f0731a)
![Profile](https://github.com/user-attachments/assets/ea366706-55c2-4d49-adf6-a83d773dc3cb)

