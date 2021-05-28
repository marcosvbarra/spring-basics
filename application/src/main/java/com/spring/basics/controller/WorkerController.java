package com.spring.basics.controller;

import com.spring.basics.WorkerService;
import com.spring.basics.api.WorkerApi;
import com.spring.basics.api.request.CreateWorkerRequest;
import com.spring.basics.api.request.UpdateWorkerRequest;
import com.spring.basics.api.response.CreateWorkerResponse;
import com.spring.basics.api.response.WorkerResponse;
import com.spring.basics.entity.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class WorkerController implements WorkerApi {

    @Autowired
    private WorkerService workerService;

    @Override
    public CreateWorkerResponse createWorker(@RequestBody @Valid CreateWorkerRequest workerRequest) {
        return workerService.createWorker(workerRequest);
    }

    @Override
    public WorkerResponse findWorkerById(@PathVariable Long id) {
        return workerService.findWorkerById(id);
    }

    @Override
    public WorkerResponse findWorkerByCpf(@PathVariable String cpf) {
        return workerService.findWorkerByCpf(cpf);
    }

    @Override
    public Worker updateWorker(@RequestBody @Valid UpdateWorkerRequest workerRequest, @PathVariable Long id) {
        return workerService.updateWorker(workerRequest, id);
    }

    @Override
    public void deleteWorker(@PathVariable Long id) {
        workerService.deleteWorker(id);
    }
}
