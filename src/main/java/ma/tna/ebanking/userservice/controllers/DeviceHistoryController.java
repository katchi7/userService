package ma.tna.ebanking.userservice.controllers;

import ma.tna.audit.controller.GenericAuditController;
import ma.tna.audit.service.GenericHistoryService;
import ma.tna.ebanking.userservice.model.Device;
import ma.tna.ebanking.userservice.repositories.DeviceRepo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/history/device")
public class DeviceHistoryController extends GenericAuditController<Device,Long,Long,DeviceRepo> {
    public DeviceHistoryController(GenericHistoryService<Device, Long, Long> historyService, DeviceRepo repository) {
        super(historyService, repository);
    }
}