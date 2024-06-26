package com.fastcampus.loan.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.fastcampus.loan.domain.Counsel;
import com.fastcampus.loan.dto.CounselDTO.Request;
import com.fastcampus.loan.dto.CounselDTO.Response;
import com.fastcampus.loan.exception.BaseException;
import com.fastcampus.loan.exception.ResultType;
import com.fastcampus.loan.repository.CounselRepository;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class CounselServiceTest {

  @InjectMocks
  CounselServiceImpl counselService;

  @Mock
  private CounselRepository counselRepository;

  @Spy
  private ModelMapper modelMapper;

  @Test
  void Should_ReturnResponseOfNewCounselEntity_When_RequestCounsel() {
    Counsel entity = Counsel.builder()
        .name("Member Kim")
        .cellPhone("010-1111-2222")
        .email("mail@abc.de")
        .memo("I hope to get a loan")
        .zipCode("123456")
        .address("Somewhere in Gangnam-gu, Seoul")
        .addressDetail("What Apartment No. 101, 1st floor No. 101")
        .build();

    Request request = Request.builder()
        .name("Member Kim")
        .cellPhone("010-1111-2222")
        .email("mail@abc.de")
        .memo("I hope to get a loan")
        .zipCode("123456")
        .address("Somewhere in Gangnam-gu, Seoul")
        .addressDetail("What Apartment No. 101, 1st floor No. 101")
        .build();

    // 모킹처리된 리포지토리에 뭔값이 들어온다면 그냥 entity 리턴해
    when(counselRepository.save(ArgumentMatchers.any(Counsel.class))).thenReturn(entity);

    Response actual = counselService.create(request);

    assertThat(actual.getName()).isSameAs(entity.getName());
  }

  @Test
  void Should_ReturnResponseOfExistCounselEntity_When_RequestExistCounselId() {
    Long findId = 1L;

    Counsel entity = Counsel.builder()
        .counselId(1L)
        .build();

    when(counselRepository.findById(findId)).thenReturn(Optional.ofNullable(entity));

    Response actual = counselService.get(1L);

    assertThat(actual.getCounselId()).isSameAs(findId);
  }

  @Test
  void Should_ThrowException_When_RequestNotExistCounselId() {
    Long findId = 3L;

    when(counselRepository.findById(findId)).thenThrow(new BaseException(ResultType.SYSTEM_ERROR));

    Assertions.assertThrows(BaseException.class, () -> counselService.get(3L));
  }

  @Test
  void Should_ReturnUpdatedResponseOfExistCounselEntity_When_RequestUpdateExistCounselInfo() {
    // 1. 테스트 코드 작성 요령
    // 나와야하는 값 우리가 알고 있는 값
    // 2. 언제할거냐?  모킹처리를 한 리포지토리를 가지고, 그런 행위를 했을 때
    // 기본적으로 주어진 값을 반환하게 해
    // 3. 그러면 실제값은 서비스에 집어넣으면 나오는  response 값이기 때문에
    // 받아온 값이 우리가 알고있는 값이랑 같느냐? 라고 테스트하면됨.
    Long findId = 1L;

    Counsel entity = Counsel.builder()
        .counselId(1L)
        .name("Member Kim")
        .build();

    Request request = Request.builder()
        .name("Member Kang")
        .build();

    when(counselRepository.save(ArgumentMatchers.any(Counsel.class))).thenReturn(entity);
    when(counselRepository.findById(findId)).thenReturn(Optional.ofNullable(entity));

    Response actual = counselService.update(findId, request);

    assertThat(actual.getCounselId()).isSameAs(findId);
    assertThat(actual.getName()).isSameAs(request.getName());
  }

  @Test
  void Should_DeletedCounselEntity_When_RequestDeleteExistCounselInfo() {
    Long targetId = 1L;

    Counsel entity = Counsel.builder()
        .counselId(1L)
        .build();

    when(counselRepository.save(ArgumentMatchers.any(Counsel.class))).thenReturn(entity);
    when(counselRepository.findById(targetId)).thenReturn(Optional.ofNullable(entity));

    counselService.delete(targetId);

    assertThat(entity.getIsDeleted()).isSameAs(true);
  }
}
