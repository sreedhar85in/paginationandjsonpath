package com.tranxactor.pagination1.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.tranxactor.pagination1.Repository.MemberRepo;
import com.tranxactor.pagination1.model.Member;
import com.tranxactor.pagination1.model.MembersSearchCriteria;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.criteria.Predicate;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MemberService {
    private MemberRepo memberRepo;

    @Autowired
    public MemberService(MemberRepo memberRepo) {
        this.memberRepo = memberRepo;
    }
    
   
   String json = "[{\"name\":\"Estonia\",\"topLevelDomain\":[\".ee\"],\"alpha2Code\":\"EE\",\"alpha3Code\":\"EST\",\"callingCodes\":[\"372\"],\"capital\":\"Tallinn\",\"altSpellings\":[\"EE\",\"Eesti\",\"Republic of Estonia\",\"Eesti Vabariik\"],\"region\":\"Europe\",\"subregion\":\"Northern Europe\",\"population\":1315944,\"latlng\":[59.0,26.0],\"demonym\":\"Estonian\",\"area\":45227.0,\"gini\":36.0,\"timezones\":[\"UTC+02:00\"],\"borders\":[\"LVA\",\"RUS\"],\"nativeName\":\"Eesti\",\"numericCode\":\"233\",\"currencies\":[{\"code\":\"EUR\",\"name\":\"Euro\",\"symbol\":\"€\"}],\"languages\":[{\"iso639_1\":\"et\",\"iso639_2\":\"est\",\"name\":\"Estonian\",\"nativeName\":\"eesti\"}],\"translations\":{\"de\":\"Estland\",\"es\":\"Estonia\",\"fr\":\"Estonie\",\"ja\":\"エストニア\",\"it\":\"Estonia\",\"br\":\"Estônia\",\"pt\":\"Estónia\",\"nl\":\"Estland\",\"hr\":\"Estonija\",\"fa\":\"استونی\"},\"flag\":\"https://restcountries.eu/data/est.svg\",\"regionalBlocs\":[{\"acronym\":\"EU\",\"name\":\"European Union\",\"otherAcronyms\":[],\"otherNames\":[]}],\"cioc\":\"EST\"}]";
    
   


    public Page<Member> findAllMembers(MembersSearchCriteria searchCriteria) {
    	
    	  String jsonPath = "$.[*].name";  
    	    DocumentContext jsonContext = JsonPath.parse(json);
    	    List<String> result = jsonContext.read(jsonPath);
    	    log.info("name :: "+result.get(0));
    	    
    	    result = jsonContext.read("$.[*].capital"); //To get Captial
    	    log.info("Capital :: "+result.get(0));   
    	
        PageRequest pageRequest = genPageRequest(searchCriteria.getPage(), searchCriteria.getPerPage(),
                searchCriteria.getDirection(), searchCriteria.getSort());
        return memberRepo.findAll(getMemberSpecification(searchCriteria), pageRequest);
    }

    private PageRequest genPageRequest(int page, int limit, Sort.Direction direction, String sort) {
      //  return new PageRequest(page - 1, limit, new Sort(direction, sort));
      return  PageRequest.of(page - 1, limit, Sort.by(direction, sort));
    }

    private Specification<Member> getMemberSpecification(MembersSearchCriteria filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filter.getId() != null) {
                predicates.add(cb.equal(root.get("id"), filter.getId()));
            }
            if (!StringUtils.isEmpty(filter.getFirstName())) {
                predicates.add(cb.like(cb.lower(root.get("firstName")),
                        "%" + filter.getFirstName().toLowerCase() + "%"));
            }
            if (!StringUtils.isEmpty(filter.getLastName())) {
                predicates.add(cb.like(cb.lower(root.get("lastName")),
                        "%" + filter.getLastName().toLowerCase() + "%"));
            }
            if (!StringUtils.isEmpty(filter.getNikename())) {
                predicates.add(cb.like(cb.lower(root.get("nickName")),
                        "%" + filter.getNikename().toLowerCase() + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}