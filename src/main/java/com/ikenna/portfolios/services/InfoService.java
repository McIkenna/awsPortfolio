package com.ikenna.portfolios.services;

import com.ikenna.portfolios.components.IInfoService;
import com.ikenna.portfolios.exceptions.InfoException;
import com.ikenna.portfolios.infos.Info;
import com.ikenna.portfolios.repository.AddressRepository;
import com.ikenna.portfolios.repository.InfoRepository;
import com.ikenna.portfolios.repository.SkillsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InfoService implements IInfoService {

    @Autowired
    private InfoRepository infoRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private SkillsRepository skillsRepository;



    public Info saveOrUpdateInfo(Info info){
       try{

           return infoRepository.save(info);

       }catch (Exception e){
           throw new InfoException("The user with phone number '" + info.getPhone() + "' already exist");

       }

    }

    public Info findInfoByPhoneNumber(String phoneNo){
        Info info = infoRepository.findByPhone(phoneNo);

        if(info == null){
            throw new InfoException("The user with phone number '" + phoneNo + "' does not exist");
        }
        return info;
    }

    public Iterable<Info> findAllInfos(){
        return infoRepository.findAll();
    }


    public void deleteInfoByPhoneNo(String phoneNo){
        Info info = infoRepository.findByPhone(phoneNo);

        if(info == null){
            throw new InfoException("Cannot delete, '" + phoneNo + "' does not exist");
        }
        infoRepository.delete(info);
    }
}
