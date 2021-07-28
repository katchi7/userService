package ma.tna.ebanking.userservice.controllers;

import ma.tna.audit.controller.AuditController;
import ma.tna.audit.service.HistoryService;
import ma.tna.ebanking.userservice.model.Device;
import ma.tna.ebanking.userservice.repositories.DeviceRepo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/history/device")
public class DeviceHistoryController extends AuditController<Device, DeviceRepo> {
    public DeviceHistoryController(HistoryService<Device> historyService, DeviceRepo repository) {
        super(historyService, repository);
    }
}
