package com.ontotext.graphdb.repository.sparql;

import org.eclipse.rdf4j.repository.config.RepositoryConfigException;
import org.eclipse.rdf4j.repository.config.RepositoryImplConfig;
import org.eclipse.rdf4j.repository.sparql.SPARQLRepository;
import org.eclipse.rdf4j.repository.sparql.config.SPARQLRepositoryFactory;

/**
 * An extension of {@link SPARQLRepositoryFactory} that adds support for specifying username and password.
 *
 * The factory is accessible by specifying graphdb:SPARQLRepository in the repository config.ttl.
 */
public class SPARQLRepositoryAuthFactory extends SPARQLRepositoryFactory {
    public static final String REPOSITORY_TYPE = "graphdb:SPARQLRepository";

    @Override
    public String getRepositoryType() {
        return REPOSITORY_TYPE;
    }

    @Override
    public RepositoryImplConfig getConfig() {
        return new SPARQLRepositoryAuthConfig();
    }

    @Override
    public SPARQLRepository getRepository(RepositoryImplConfig config) throws RepositoryConfigException {
        SPARQLRepository repository = super.getRepository(config);
        if (config instanceof SPARQLRepositoryAuthConfig) {
            String username = ((SPARQLRepositoryAuthConfig) config).getUsername();
            String password = ((SPARQLRepositoryAuthConfig) config).getPassword();
            if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
                repository.setUsernameAndPassword(username, password);
            }
        }

        return repository;
    }
}
