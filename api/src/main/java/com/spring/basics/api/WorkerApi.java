package com.spring.basics.api;


import com.spring.basics.api.request.CreateWorkerRequest;
import com.spring.basics.api.request.UpdateWorkerRequest;
import com.spring.basics.api.response.CreateWorkerResponse;
import com.spring.basics.api.response.WorkerResponse;
import com.spring.basics.entity.Worker;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/workers")
public interface WorkerApi {

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    CreateWorkerResponse createWorker(@RequestBody CreateWorkerRequest workerRequest);

    @GetMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    WorkerResponse findWorkerById(@PathVariable Long id);

    @GetMapping(value = "/cpf/{cpf}")
    @ResponseStatus(value = HttpStatus.OK)
    WorkerResponse findWorkerByCpf(@PathVariable String cpf);

    @PutMapping(value = "{id}")
    @ResponseStatus(value = HttpStatus.OK)
    Worker updateWorker(@RequestBody UpdateWorkerRequest workerRequest, @PathVariable Long id);

    @DeleteMapping(value = "{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void deleteWorker(@PathVariable Long id);


}
