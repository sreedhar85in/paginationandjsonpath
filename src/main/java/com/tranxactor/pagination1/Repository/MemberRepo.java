package com.tranxactor.pagination1.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tranxactor.pagination1.model.Member;

public interface MemberRepo extends JpaRepository<Member, Long>, JpaSpecificationExecutor<Member>  {

	void save(List<Member> memberList);



}
