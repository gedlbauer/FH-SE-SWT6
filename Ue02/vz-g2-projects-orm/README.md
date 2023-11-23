# O/R-Mapping mit Hibernate und JPA

## Teil 1: Einführung in O/R-Mapping: `EmployeeManager`

* Projekt aufsetzen
* Hibernate-Konfiguration erstellen
* Domänenklasse `Employee` erstellen
* Mapping für `Employee` mit Config-File (`Employee.hbm.xml`)
* Persistenzcode implementieren
* `HibernateUtil` entwicklen
* Testen
    +  Datenbank-View konfigurieren
    + Hauptprogramm starten

## Teil 2: Mapping-Konzepte: `WorkLogManager`

* Maven-Modul hinzufügen
* JPA-Konfiguration erstellen (`persistence.xml`)
* Arbeiten mit `EntityManager`
* Mapping für `Employee` mit Annotationen definieren
* Mapping der Assoziation `Employee` -> `LogbookEntry` (`OneToMany` und `ManyToOne`)
* Mapping der Assoziation `Employee` -> `Address` (`@OneToOne` und `@Embedded`)
* Mapping der Assoziation `Employee` <-> `Project` (`ManyToMany`)
* Mapping von Vererbungshierarchien
    + `@Inheritance.JOINED`
    + `@Inheritance.TABLE_PER_CLASS`
    + `@Inheritance.SINGLE.TABLE`
* Ladestrategien (Eager und Lazy Fetching)
* Queries

