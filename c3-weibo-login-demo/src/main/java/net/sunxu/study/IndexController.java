package net.sunxu.study;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.plugin.liveconnect.SecurityContextHelper;

@RestController
public class IndexController {
    @GetMapping("/")
    public Object index() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
