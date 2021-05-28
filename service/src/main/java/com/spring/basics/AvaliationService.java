package com.spring.basics;

import com.spring.basics.api.request.CreateAvaliationRequest;
import com.spring.basics.entity.Avaliation;
import com.spring.basics.entity.Customer;
import com.spring.basics.entity.Worker;
import com.spring.basics.exception.AvaliationNotFoundException;
import com.spring.basics.exception.CustomerNotFoundException;
import com.spring.basics.exception.WorkerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.lang.Math.round;

@Service
public class AvaliationService {


    @Autowired
    private AvaliationRepository avaliationRepository;
    @Autowired
    private WorkerRepository workerRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public Avaliation createAvaliation(CreateAvaliationRequest avaliationRequest) {


        Optional<Customer> customer = customerRepository.findById(avaliationRequest.getIdCustomer());
        if (customer.isEmpty())
            throw new CustomerNotFoundException();
        Optional<Worker> worker = workerRepository.findById(avaliationRequest.getIdWorker());
        if (worker.isEmpty())
            throw new WorkerNotFoundException();

        Avaliation avaliation = getAvaliationByRequest(avaliationRequest);
        List<Avaliation> workerAvaliations = avaliationRepository.findAllByIdWorker(avaliationRequest.getIdWorker());
        workerAvaliations.add(avaliation);

        updateWorkerRating(worker.get(), workerAvaliations);


        return avaliationRepository.saveAndFlush(avaliation);
    }

    public void deleteAvaliation(Long id) {

        Optional<Avaliation> avaliation = avaliationRepository.findById(id);
        if (avaliation.isEmpty())
            throw new AvaliationNotFoundException();

        avaliationRepository.deleteById(id);

        Optional<Worker> worker = workerRepository.findById(avaliation.get().getIdWorker());
        if (worker.isPresent()){
            List<Avaliation> workerAvaliations = avaliationRepository.findAllByIdWorker(avaliation.get().getIdWorker());
            updateWorkerRating(worker.get(), workerAvaliations);
        }
    }

    private void updateWorkerRating(Worker worker, List<Avaliation> workerAvaliations) {
        injectNewWorkerRating(workerAvaliations, worker);
        workerRepository.saveAndFlush(worker);
    }

    private void injectNewWorkerRating(List<Avaliation> workerAvaliations, Worker worker) {
        double totalRating = workerAvaliations.stream().mapToDouble(Avaliation::getRating).sum();
        double workerRating = totalRating / workerAvaliations.size();
        double scale = Math.pow(10, 1);
        double roundedWorkerRating =  Math.round(workerRating * scale) / scale;

        worker.setRating(roundedWorkerRating);
    }

    private Avaliation getAvaliationByRequest(CreateAvaliationRequest avaliationRequest) {
        Avaliation avaliation = new Avaliation();
        avaliation.setIdWorker(avaliationRequest.getIdWorker());
        avaliation.setIdCustomer(avaliationRequest.getIdCustomer());
        avaliation.setRating(avaliationRequest.getRating());
        avaliation.setDescription(avaliationRequest.getDescription());
        avaliation.setCreatedAt(LocalDateTime.now());
        return avaliation;
    }


}
