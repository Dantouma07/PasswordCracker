# PasswordCracker v1 - Outil de vérification de robustesse des mots de passe

[![Java Version](https://img.shields.io/badge/Java-17%2B-orange.svg)](https://www.oracle.com/java/)
[![Pattern](https://img.shields.io/badge/Pattern-Simple%20Factory-blue.svg)](https://refactoring.guru/fr/design-patterns/factory-method)

## 1. Introduction
Ce projet a été réalisé dans le cadre du mini-projet 1 visant à mettre en œuvre une architecture logicielle modulaire et découplée en Java. L'objectif principal est d'appliquer le patron de conception **Simple Factory** (Fabrique Simple) à travers un cas d'usage concret en cybersécurité : le cassage de hashs cryptographiques.

## 2. Présentation du problème
Dans le domaine de la sécurité informatique, les mots de passe ne sont jamais stockés en clair afin d'éviter leur fuite en cas de compromission de la base de données. Ils sont transformés via des fonctions de hachage unidirectionnelles. 

Lors d'un audit de sécurité, il est crucial de tester la résistance de ces hashs face à deux types d'attaques standard :
* **L'attaque par dictionnaire (DICO) :** Test exhaustif d'une liste prédéfinie de mots probables.
* **L'attaque par force brute (BRUTE) :** Génération combinatoire de toutes les possibilités de chaînes de caractères (ici, limitées aux minuscules de `a-z` jusqu'à une longueur maximale de 4 caractères).

L'application `PasswordCracker` reçoit un hash MD5 ainsi qu'une méthode, et tente de retrouver le mot de passe d'origine.

## 3. Architecture
L'architecture logicielle repose sur le polymorphisme et l'encapsulation de chaque stratégie de cassage. 
* Une interface commune `HashCracker` dicte le comportement de toutes les stratégies.
* Le programme principal (`PasswordCracker`) n'instancie jamais directement les implémentations concrètes. Il délègue cette responsabilité à une fabrique centralisée.

### Description des responsabilités des classes
* **`HashCracker` (Interface) :** Définit le contrat obligatoire `crack(String hash)` pour tous les algorithmes de cassage.
* **`DictionaryHashCracker` :** Charge un fichier dictionnaire, applique la fonction MD5 sur chaque mot et le compare au hash cible.
* **`BruteForceHashCracker` :** Génère les combinaisons de `a` à `zzzz` de manière itérative ou récursive pour trouver une correspondance.
* **`HashCrackerFactory` :** Centralise la logique de création. Elle analyse l'argument de type textuel ou énuméré (`DICO`/`BRUTE`) et retourne l'instance concrète appropriée.

## 4. Diagramme UML
L'organisation des classes et leur couplage faible sont modélisés ci-dessous :

```mermaid
classDiagram
    class HashCracker {
        <<interface>>
        +crack(hash: String) String
    }

    class DictionaryHashCracker {
        -dictionaryPath: String
        +crack(hash: String) String
    }

    class BruteForceHashCracker {
        -alphabet: String
        -maxLength: int
        +crack(hash: String) String
    }

    class HashCrackerFactory {
        +create(method: String) HashCracker$
    }

    class PasswordCracker {
        +main(args: String[])
    }

    HashCracker <|.. DictionaryHashCracker
    HashCracker <|.. BruteForceHashCracker
    HashCrackerFactory ..> HashCracker : "instantiates"
    PasswordCracker ..> HashCrackerFactory : "requests"
    PasswordCracker ..> HashCracker : "uses"