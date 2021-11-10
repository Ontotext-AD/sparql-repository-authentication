An extension of RDF4J's SPARQLRepositoryFactory that adds support for specifying username and password. It adds support for a new repository type `graphdb:SPARQLRepository`.

Simply build the project and drop the resulting jar file into GraphDB's lib directory.

Then create a repository with a config.ttl similar to this (substituting the desired repository ID, query URL, username and password):

```turtle
@prefix rep: <http://www.openrdf.org/config/repository#> .
@prefix sparql: <http://www.openrdf.org/config/repository/sparql#> .

<#remote> a rep:Repository;
    rep:repositoryID "remote";
    rep:repositoryImpl [
        rep:repositoryType "graphdb:SPARQLRepository";
        sparql:query-endpoint <http://query.example.com/sparql>;
        sparql:username "myUser";
        sparql:password "myPassword";
    ];
    rdfs:label "remote" .
```
