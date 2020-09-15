package com.tranxactor.pagination1;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.tranxactor.pagination1.Repository.MemberRepo;
import com.tranxactor.pagination1.model.Member;

@SpringBootApplication
public class PaginationProject1Application {
	
	private final MemberRepo memberRepo;
	
	 	@Autowired
	    public PaginationProject1Application(MemberRepo memberRepo) {
	        this.memberRepo = memberRepo;
	    }

	public static void main(String[] args) {
		SpringApplication.run(PaginationProject1Application.class, args);
	}
	
	
	CommandLineRunner runner() {
		
		return ags -> {
			
		memberRepo.save(getMemberList());
		memberRepo.flush();
			
			
		};
	}
	
	
	private List<Member> getMemberList(){
		
		Member member1 = new Member();
        member1.setFirstName("Cherprang");
        member1.setLastName("Areekul");
        member1.setNickName("Cherprang");
        Member member2 = new Member();
        member2.setFirstName("Jennis");
        member2.setLastName("Oprasert");
        member2.setNickName("Jennis");
        Member member3 = new Member();
        member3.setFirstName("Praewa");
        member3.setLastName("Suthamphong");
        member3.setNickName("Music");
        return Arrays.asList(member1, member2, member3);
		
	}

}
