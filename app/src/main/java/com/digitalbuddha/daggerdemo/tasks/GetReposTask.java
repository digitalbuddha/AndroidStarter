package com.digitalbuddha.daggerdemo.tasks;

import com.digitalbuddha.daggerdemo.rest.Github;
import com.digitalbuddha.daggerdemo.models.Repo;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by MikeN on 8/3/14.
 */
public class GetReposTask extends InjectTask{

    @Inject
    public Github service;
    public String userName;
    public List<Repo> repos;

    @Inject
    public GetReposTask() {
    }


    @Override
    protected void run() {
        repos = service.listRepos(userName);
    }
}
