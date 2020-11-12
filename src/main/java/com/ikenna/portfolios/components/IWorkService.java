package com.ikenna.portfolios.components;

import com.ikenna.portfolios.infos.Work;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface IWorkService {
    public Work saveOrUpdateWork(MultipartFile file, Work work);
    public Work findWorkByCompanyName(String companyName);
}
