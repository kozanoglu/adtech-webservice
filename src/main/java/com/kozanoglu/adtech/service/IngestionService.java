package com.kozanoglu.adtech.service;

import com.kozanoglu.adtech.controller.exception.ClickNotFoundException;
import com.kozanoglu.adtech.controller.exception.DeliveryNotFoundException;
import com.kozanoglu.adtech.dto.request.ClickRequestDTO;
import com.kozanoglu.adtech.dto.request.DeliveryRequestDTO;
import com.kozanoglu.adtech.dto.request.InstallRequestDTO;
import com.kozanoglu.adtech.entity.Click;
import com.kozanoglu.adtech.entity.Delivery;
import com.kozanoglu.adtech.entity.Install;
import com.kozanoglu.adtech.repository.ClickRepository;
import com.kozanoglu.adtech.repository.DeliveryRepository;
import com.kozanoglu.adtech.repository.InstallRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Service;

@Service
public class IngestionService {

    private static final Logger LOG = LoggerFactory.getLogger(IngestionService.class);

    private DeliveryRepository deliveryRepository;
    private ClickRepository clickRepository;
    private InstallRepository installRepository;

    @Autowired
    public IngestionService(final DeliveryRepository deliveryRepository, final ClickRepository clickRepository, final InstallRepository installRepository) {
        this.deliveryRepository = deliveryRepository;
        this.clickRepository = clickRepository;
        this.installRepository = installRepository;
    }

    public void saveDelivery(DeliveryRequestDTO requestDTO) {
        try {
            Delivery delivery = new Delivery();
            delivery.setAdvertisementId(requestDTO.getAdvertisementId());
            delivery.setDeliveryId(requestDTO.getDeliveryId());
            delivery.setBrowser(requestDTO.getBrowser());
            delivery.setOs(requestDTO.getOs());
            delivery.setSite(requestDTO.getSite());
            delivery.setTime(requestDTO.getTime());
            deliveryRepository.save(delivery);
        } catch (Exception e) {
            LOG.error("Failed to save Delivery to DB", e);
            throw new RuntimeException(e);
        }
    }

    public void saveClick(ClickRequestDTO requestDTO) throws DeliveryNotFoundException {
        try {
            Click click = new Click();
            click.setClickId(requestDTO.getClickId());
            click.setDelivery(new Delivery(requestDTO.getDeliveryId()));
            click.setTime(requestDTO.getTime());
            clickRepository.save(click);
        } catch (JpaObjectRetrievalFailureException e) {
            LOG.error("Unable to find Delivery wth id {}", requestDTO.getDeliveryId(), e);
            throw new DeliveryNotFoundException(e);
        } catch (Exception e) {
            LOG.error("Failed to save Click to DB", e);
            throw new RuntimeException(e);
        }
    }

    public void saveInstall(InstallRequestDTO requestDTO) throws ClickNotFoundException {
        try {
            Install install = new Install();
            install.setInstallId(requestDTO.getInstallId());
            install.setClick(new Click(requestDTO.getClickId()));
            install.setTime(requestDTO.getTime());
            installRepository.save(install);
        } catch (JpaObjectRetrievalFailureException e) {
            LOG.error("Unable to find Click wth id {}", requestDTO.getClickId(), e);
            throw new ClickNotFoundException(e);
        } catch (Exception e) {
            LOG.error("Failed to save Install to DB", e);
            throw new RuntimeException(e);
        }
    }
}
