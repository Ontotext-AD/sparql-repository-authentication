package com.ontotext.graphdb.repository.sparql;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.util.ModelException;
import org.eclipse.rdf4j.model.util.Models;
import org.eclipse.rdf4j.repository.config.RepositoryConfigException;
import org.eclipse.rdf4j.repository.sparql.config.SPARQLRepositoryConfig;

/**
 * An extension of {@link SPARQLRepositoryConfig} that adds support for specifying username and password.
 */
public class SPARQLRepositoryAuthConfig extends SPARQLRepositoryConfig {
    private static final ValueFactory vf = SimpleValueFactory.getInstance();

    public static final IRI USERNAME = vf.createIRI(NAMESPACE, "username");

    public static final IRI PASSWORD = vf.createIRI(NAMESPACE, "password");

    private String username;

    private String password;

    @Override
    public Resource export(Model m) {
        Resource implNode = super.export(m);
        if (getUsername() != null) {
            m.add(implNode, USERNAME, vf.createLiteral(getUsername()));
        }
        if (getPassword() != null) {
            m.add(implNode, PASSWORD, vf.createLiteral(getPassword()));
        }
        return implNode;
    }

    @Override
    public void parse(Model m, Resource implNode) throws RepositoryConfigException {
        super.parse(m, implNode);

        try {
            Models.objectLiteral(m.getStatements(implNode, USERNAME, null))
                    .ifPresent(lit -> setUsername(lit.stringValue()));
            Models.objectLiteral(m.getStatements(implNode, PASSWORD, null))
                    .ifPresent(lit -> setPassword(lit.stringValue()));
        } catch (ModelException e) {
            throw new RepositoryConfigException(e.getMessage(), e);
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
