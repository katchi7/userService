package ma.tna.ebanking.userservice.controllers;

import controller.AuditController;
import ma.tna.ebanking.userservice.model.Device;
import ma.tna.ebanking.userservice.repositories.DeviceRepo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.HistoryService;
@RestController
@RequestMapping("/history/device")
public class DeviceHistoryController extends AuditController<Device, DeviceRepo> {
    public DeviceHistoryController(HistoryService<Device> historyService, DeviceRepo repository) {
        super(historyService, repository);
    }
}
