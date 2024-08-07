package com.hubo.gillajabi.agreement.application.dto.request;

import com.hubo.gillajabi.agreement.application.dto.AgreementItem;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Service
public class AgreementRequest {
    List<AgreementItem> agreements;
}
