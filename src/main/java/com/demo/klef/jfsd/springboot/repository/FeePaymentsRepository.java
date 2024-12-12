package com.demo.klef.jfsd.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.klef.jfsd.springboot.model.FeePayments;
import com.demo.klef.jfsd.springboot.model.Student;

@Repository
public interface FeePaymentsRepository extends JpaRepository<FeePayments, Integer> {

    List<FeePayments> findByStudent(Student student);
    List<FeePayments> findByFeeType(String type);
}