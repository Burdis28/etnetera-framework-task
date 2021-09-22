package com.etnetera.hr.repository;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for obtaining business data logic.
 */
@Service
public class JavaScriptFrameworkService extends CrudService<JavaScriptFramework, Long> {

    private JavaScriptFrameworkRepository repository;

    @Autowired
    public JavaScriptFrameworkService(JavaScriptFrameworkRepository repository) {
        this.repository = repository;
    }

    @Override
    protected CrudRepository<JavaScriptFramework, Long> getRepository() {
        return repository;
    }

    public void create(JavaScriptFramework framework) {
        try {
            repository.save(framework);
        } catch (RuntimeException exception) {
            throw new ServiceException(exception.getMessage());
        }
    }

    public void create(List<JavaScriptFramework> frameworks) {
        try {
            repository.saveAll(frameworks);
        } catch (RuntimeException exception) {
            throw new ServiceException(exception.getMessage());
        }
    }

    public void delete(JavaScriptFramework framework) {
        try {
            repository.delete(framework);
        } catch (RuntimeException exception) {
            throw new ServiceException(exception.getMessage());
        }
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (RuntimeException exception) {
            throw new ServiceException(exception.getMessage());
        }
    }
}
