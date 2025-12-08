# Firebase Realtime Database

**Firebase Realtime Database** je NoSQL dokumentna oblačna baza podatkov, optimizirano za realnočasovno sinhronizacijo med več napravami, ki je del *Google Firebase* ekosistema. Podatki so shranjeni v JSON drevesni strukturi, vse spremembe pa se takoj propagirajo vsem povezanim klientom.

Močno je povezana s *Firebase Auth*, *Firebase Hosting* in *Cloud Functions*, kar omogoča celoten ekosistem za razvoj platformno odvisnih aplikacij. Predvsem je uporabljen pri razvoju mobilnih aplikacij za operacijske sisteme Android in iOS, ter za spletne aplikacije.

---

## Zakaj Firebase Realtime Database?

Obstaja veliko razlogov zakaj bi si izbrali Firebase Realtime Database, med katerim so glavni:

- Potreba po real-time komunikaciji (npr. za klepet, multiplayer igre, IoT)
- Ne želimo postavljati lastnih strežnikov ali baz podatkov
- Primeren za hitro izdelavo začetnih verzij aplikacij, kjer želimo čim prej pokazati osnovno funkcionalnost (Minimal Viable Product)
- Firebase je standard pri razvoju mobilnih aplikacij (Android/iOS)
- Avtomatsko skaliranje in "serverless" upravljanje

---

## Arhitektura in način delovanja

### Struktura podatkov
Podatki so shranjeni kot *JSON* dokument, organiziran v drevo, podobno kot velik *"key-value"* objekt.

Če delamo aplikacijo, ki podpira pošiljanje sporočil, struktura bi izgledala tako:

```json
{
  "users": {
    "user1": { "name": "Uros", "age": 33 },
    "user2": { "name": "Ana", "age": 19 }
  },
  "messages": {
    "msg01": { "from": "user1", "text": "hello" }
  }
}
```

### Real-time posodobitve
Firebase uporablja **WebSockets** (spletne vtičnice), tehnologijo, ki omogoča dvosmerno, real-time komunikacijo.

Ko se en uporabnik poveže na bazo:
1. Pridobi začetne podatke.
2. Naroči se na spremembe na določenem "path"-u.
3. Firebase naloži spremembe takoj, brez ponovnih zahtev.

To omogoča real-time aplikacije brez kompleksnih backend rešitev.

### Offline način
Firebase klient (Android/iOS/Web) hrani lokalni predpomnilnik, spremembe zapisuje lokalno in sinhronizira z oblakom, ko ponovno vzpostavi povezavo z internetom.

---

## Prednosti
### ✔ Real-time sinhronizacija
Ni polling-a. Spremembe so vidne instantno.

### ✔ Offline-first aplikacije
Idealno za mobilne aplikacije in IoT senzorje.

### ✔ Ni potrebe po infrastrukturi
Google upravlja:
- strežnike
- skaliranje
- posodobitve
- varnostne popravke

### ✔ Preprost API

### ✔ Dobro integrirana varnost
Security Rules omogoča:
- Avtentikacijske politike
- Pravila dostopa po path-u
- Validacijo strukture podatkov

### ✔ Odlično za MVP-je in startupe

---

## Slabosti
### ✖ Omejene poizvedbe
Ni kompleksnih SQL poizvedb, kot so *JOIN* in *GROUP BY*. Podatke je treba struktuirati tako, da so poizvedbe preproste.

### ✖ Podvajanje podatkov
Ker je drevo JSON, pogosto je treba duplicirati podatke. Kompleksen join pomeni kopiranje v več path-ov. Obstaja možnost nekonistentnosti, če se ne upravlja pravilno.

### ✖ Potencialno drago pri velikem prometu
Real-time aplikacije, ki veliko berejo, lahko generirajo stroške. Cena je pa odvisna od prometa in porabe prostora.

### ✖ Manj primerno za velike relacijske baze
Firebase je boljša izbira za preproste, hierarhične podatke.
