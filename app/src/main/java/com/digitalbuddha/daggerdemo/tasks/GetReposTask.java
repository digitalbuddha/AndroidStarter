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
    public Github api;
    public String userName;
    public List<Repo> repos;



    @Override
    protected void run() {
        repos = api.repos(userName);
    }
}
