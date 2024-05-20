package com.fastcampus.loan.service;

import com.fastcampus.loan.domain.Counsel;
import com.fastcampus.loan.dto.CounselDTO.Request;
import com.fastcampus.loan.dto.CounselDTO.Response;
import com.fastcampus.loan.exception.BaseException;
import com.fastcampus.loan.exception.ResultType;
import com.fastcampus.loan.repository.CounselRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // di를 위한 어노테이션
public class CounselServiceImpl implements CounselService {

  private final CounselRepository counselRepository;

  // 맵핑을 따로 빌더로 해줄필요없이 다 해준다. 
  private final ModelMapper modelMapper;

  @Override
  public Response create(Request request) {
    // 엔티티로 맵핑해준다.
    Counsel counsel = modelMapper.map(request, Counsel.class);
    // 로컬데이트 타임을 설정
    counsel.setAppliedAt(LocalDateTime.now());

    // 체번된 엔티티가 반환됨.
    Counsel created = counselRepository.save(counsel);

    // 응답 엔티티를 반환
    return modelMapper.map(created, Response.class);
  }

  @Override
  public Response get(Long counselId) {
    Counsel counsel = counselRepository.findById(counselId).orElseThrow(() -> {
      throw new BaseException(ResultType.SYSTEM_ERROR);
    });

    return modelMapper.map(counsel, Response.class);
  }

  @Override
  public Response update(Long counselId, Request request) {
    Counsel counsel = counselRepository.findById(counselId).orElseThrow(() -> {
      throw new BaseException(ResultType.SYSTEM_ERROR);
    });

    counsel.setName(request.getName());
    counsel.setCellPhone(request.getCellPhone());
    counsel.setEmail(request.getEmail());
    counsel.setMemo(request.getMemo());
    counsel.setAddress(request.getAddress());
    counsel.setAddressDetail(request.getAddressDetail());
    counsel.setZipCode(request.getZipCode());

    counselRepository.save(counsel);

    return modelMapper.map(counsel, Response.class);
  }

  @Override
  public void delete(Long counselId) {
    Counsel counsel = counselRepository.findById(counselId).orElseThrow(() -> {
      throw new BaseException(ResultType.SYSTEM_ERROR);
    });

    counsel.setIsDeleted(true);

    counselRepository.save(counsel);
  }
}
