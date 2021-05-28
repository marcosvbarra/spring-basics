package com.spring.basics;

import com.spring.basics.api.request.CreateWorkerRequest;
import com.spring.basics.api.response.WorkerResponse;
import com.spring.basics.entity.ActuationArea;
import com.spring.basics.entity.AreaWorker;
import com.spring.basics.entity.Avaliation;
import com.spring.basics.entity.Worker;
import com.spring.basics.exception.ActuationAreaNotFoundException;
import com.spring.basics.exception.WorkerAlreadyRegisteredException;
import com.spring.basics.exception.WorkerNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WorkerServiceTest {

    @InjectMocks
    private WorkerService workerService;

    @Mock
    private WorkerRepository workerRepository;
    @Mock
    private AreaWorkerRepository areaWorkerRepository;
    @Mock
    private ActuationAreaRepository actuationAreaRepository;
    @Mock
    private AvaliationRepository avaliationRepository;


    @Test
    public void shouldCreateAWorker() {

        CreateWorkerRequest createWorkerRequest = new CreateWorkerRequest();
        createWorkerRequest.setEmail("mar@mar.com");
        createWorkerRequest.setCpf("21524162416");
        createWorkerRequest.setActuationArea(1L);
        ActuationArea actuationArea = new ActuationArea();
        actuationArea.setId(1L);

        when(workerRepository.findByCpf(createWorkerRequest.getCpf())).thenReturn(Optional.empty());
        when(workerRepository.findByEmail(createWorkerRequest.getEmail())).thenReturn(Optional.empty());
        when(actuationAreaRepository.findById(createWorkerRequest.getActuationArea())).thenReturn(Optional.of(actuationArea));

        workerService.createWorker(createWorkerRequest);

        verify(workerRepository).saveAndFlush(any());
        verify(areaWorkerRepository).saveAndFlush(any());
    }

    @Test(expected = ActuationAreaNotFoundException.class)
    public void whenTryingToCreateAnExistentWorkerAndTheActuationAreaDoesNotExistsShouldThrowException() {

        CreateWorkerRequest createWorkerRequest = new CreateWorkerRequest();
        createWorkerRequest.setEmail("mar@mar.com");
        createWorkerRequest.setCpf("21524162416");
        createWorkerRequest.setActuationArea(1L);
        ActuationArea actuationArea = new ActuationArea();
        actuationArea.setId(1L);

        when(workerRepository.findByCpf(createWorkerRequest.getCpf())).thenReturn(Optional.empty());
        when(workerRepository.findByEmail(createWorkerRequest.getEmail())).thenReturn(Optional.empty());
        when(actuationAreaRepository.findById(createWorkerRequest.getActuationArea())).thenReturn(Optional.empty());

        workerService.createWorker(createWorkerRequest);

        verify(workerRepository, times(0)).saveAndFlush(any());
        verify(areaWorkerRepository, times(0)).saveAndFlush(any());
    }

    @Test(expected = WorkerAlreadyRegisteredException.class)
    public void whenTryingToCreateAnExistentWorkerByEmailShouldThrowException() {

        CreateWorkerRequest createWorkerRequest = new CreateWorkerRequest();
        createWorkerRequest.setEmail("mar@mar.com");
        createWorkerRequest.setCpf("21524162416");
        createWorkerRequest.setActuationArea(1L);
        ActuationArea actuationArea = new ActuationArea();
        actuationArea.setId(1L);

        when(workerRepository.findByCpf(createWorkerRequest.getCpf())).thenReturn(Optional.empty());
        when(workerRepository.findByEmail(createWorkerRequest.getEmail())).thenReturn(Optional.of(new Worker()));

        workerService.createWorker(createWorkerRequest);

        verify(workerRepository, times(0)).saveAndFlush(any());
        verify(areaWorkerRepository, times(0)).saveAndFlush(any());
    }

    @Test(expected = WorkerAlreadyRegisteredException.class)
    public void whenTryingToCreateAnExistentWorkerByCpfShouldThrowException() {

        CreateWorkerRequest createWorkerRequest = new CreateWorkerRequest();
        createWorkerRequest.setEmail("mar@mar.com");
        createWorkerRequest.setCpf("21524162416");
        createWorkerRequest.setActuationArea(1L);
        ActuationArea actuationArea = new ActuationArea();
        actuationArea.setId(1L);

        when(workerRepository.findByCpf(createWorkerRequest.getCpf())).thenReturn(Optional.of(new Worker()));

        workerService.createWorker(createWorkerRequest);

        verify(workerRepository, times(0)).saveAndFlush(any());
        verify(areaWorkerRepository, times(0)).saveAndFlush(any());
    }

    @Test
    public void shouldFindAWorkerById() {

        Worker worker = new Worker();
        worker.setId(1L);
        AreaWorker areaWorker = new AreaWorker();
        areaWorker.setIdWorker(worker.getId());
        areaWorker.setIdArea(2L);
        ActuationArea actuationArea = new ActuationArea();
        actuationArea.setId(2L);
        Avaliation avaliation = new Avaliation();
        avaliation.setId(1L);
        avaliation.setIdWorker(worker.getId());

        when(workerRepository.findById(worker.getId())).thenReturn(Optional.of(worker));
        when(areaWorkerRepository.findAllByIdWorker(worker.getId())).thenReturn(Collections.singletonList(areaWorker));
        when(actuationAreaRepository.findById(areaWorker.getIdArea())).thenReturn(Optional.of(actuationArea));
        when(avaliationRepository.findAllByIdWorker(worker.getId())).thenReturn(Collections.singletonList(avaliation));

        WorkerResponse result = workerService.findWorkerById(1L);

        assertEquals(worker.getId(), result.getId());
        assertEquals(actuationArea.getId(), result.getActuationAreas().get(0).getId());
        assertEquals(avaliation.getId(), result.getId());
    }

    @Test(expected = WorkerNotFoundException.class)
    public void whenFindingANonExistentWorkerShouldThrowException() {

        Worker worker = new Worker();
        worker.setId(1L);

        when(workerRepository.findById(worker.getId())).thenReturn(Optional.empty());
        workerService.findWorkerById(1L);

    }

    @Test
    public void shouldFindAWorkerByCpf() {

        Worker worker = new Worker();
        worker.setId(1L);
        worker.setCpf("23165498763");
        AreaWorker areaWorker = new AreaWorker();
        areaWorker.setIdWorker(worker.getId());
        areaWorker.setIdArea(2L);
        ActuationArea actuationArea = new ActuationArea();
        actuationArea.setId(2L);
        Avaliation avaliation = new Avaliation();
        avaliation.setId(1L);
        avaliation.setIdWorker(worker.getId());

        when(workerRepository.findByCpf(worker.getCpf())).thenReturn(Optional.of(worker));
        when(areaWorkerRepository.findAllByIdWorker(worker.getId())).thenReturn(Collections.singletonList(areaWorker));
        when(actuationAreaRepository.findById(areaWorker.getIdArea())).thenReturn(Optional.of(actuationArea));
        when(avaliationRepository.findAllByIdWorker(worker.getId())).thenReturn(Collections.singletonList(avaliation));

        WorkerResponse result = workerService.findWorkerByCpf(worker.getCpf());

        assertEquals(worker.getId(), result.getId());
        assertEquals(actuationArea.getId(), result.getActuationAreas().get(0).getId());
        assertEquals(avaliation.getId(), result.getId());
    }

    @Test(expected = WorkerNotFoundException.class)
    public void whenFindingANonExistentWorkerByCpfShouldThrowException() {

        Worker worker = new Worker();
        worker.setCpf("12312312312");

        workerService.findWorkerById(1L);

    }
}