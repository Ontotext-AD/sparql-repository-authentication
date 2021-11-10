package com.ontotext.graphdb.repository.sparql;

import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.config.RepositoryConfig;
import org.eclipse.rdf4j.repository.config.RepositoryConfigUtil;
import org.eclipse.rdf4j.repository.config.RepositoryFactory;
import org.eclipse.rdf4j.repository.config.RepositoryRegistry;
import org.eclipse.rdf4j.repository.sparql.SPARQLRepository;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestSPARQLRepository {
    @Test
    public void test() throws IOException, NoSuchFieldException, IllegalAccessException {
        Optional<RepositoryFactory> repositoryFactory = RepositoryRegistry.getInstance()
                .get("graphdb:SPARQLRepository");
        assertTrue(repositoryFactory.isPresent());

        Model model = Rio.parse(getClass().getResourceAsStream("/config.ttl"), "urn:", RDFFormat.TURTLE);
        RepositoryConfig repositoryConfig = RepositoryConfigUtil.getRepositoryConfig(model, "remote");

        Repository repository = repositoryFactory.get().getRepository(repositoryConfig.getRepositoryImplConfig());
        assertTrue(repository instanceof SPARQLRepository);

        Field usernameField = SPARQLRepository.class.getDeclaredField("username");
        usernameField.setAccessible(true);
        assertEquals("myUser", usernameField.get(repository));

        Field passwordField = SPARQLRepository.class.getDeclaredField("password");
        passwordField.setAccessible(true);
        assertEquals("myPassword", passwordField.get(repository));
    }
}
