package lc.platform.admin.modules.sys.controller;

import lc.platform.admin.modules.sys.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统通用请求处理
 */
@Controller
public class SysPageController {

	@Autowired
	private SysConfigService sysConfigService;

	@RequestMapping("modules/{module}/{url}.html")
	public String module(@PathVariable("module") String module, @PathVariable("url") String url){
		return "modules/" + module + "/" + url;
	}

	@RequestMapping("modules/dict.html/{type}/{name}")
	public String moduleParam(@PathVariable("type") String type,@PathVariable("name") String name, Model model){
		model.addAttribute("type",type);
		model.addAttribute("name",name);
		return "modules/sys/dict";
	}

	@RequestMapping(value = {"/", "index.html"})
	public String index(Model model){
		String sysMgtIndexPageTitle = sysConfigService.getValue("sysMgtIndexPageTitle");
		model.addAttribute("sysMgtIndexPageTitle", sysMgtIndexPageTitle);
		return "index";
	}

//	@RequestMapping("indexAdminLTE.html")
//	public String indexAdminLTE(){
//		return "indexAdminLTE";
//	}

	@RequestMapping(value="login.html")
	public String login(Model model){
		String sysMgtLoginPageTitle = sysConfigService.getValue("sysMgtLoginPageTitle");
		String sysMgtIndexPageTitle = sysConfigService.getValue("sysMgtIndexPageTitle");
		model.addAttribute("sysMgtIndexPageTitle", sysMgtIndexPageTitle);
		model.addAttribute("sysMgtLoginPageTitle", sysMgtLoginPageTitle);
		return "login";
	}

	@RequestMapping(value="login4province.html")
	public String login4province(Model model){
		String sysMgtLoginPageTitle = sysConfigService.getValue("sysMgtLoginPageTitle");
		String sysMgtIndexPageTitle = sysConfigService.getValue("sysMgtIndexPageTitle");
		model.addAttribute("sysMgtIndexPageTitle", sysMgtIndexPageTitle);
		model.addAttribute("sysMgtLoginPageTitle", sysMgtLoginPageTitle);
		return "login4province";
	}
	@RequestMapping(value="sysindex.html")
	public String sysindex(Model model){
		String sysMgtLoginPageTitle = sysConfigService.getValue("sysMgtLoginPageTitle");
		String sysMgtIndexPageTitle = sysConfigService.getValue("sysMgtIndexPageTitle");
		model.addAttribute("sysMgtIndexPageTitle", sysMgtIndexPageTitle);
		model.addAttribute("sysMgtLoginPageTitle", sysMgtLoginPageTitle);
		return "sysindex";
	}

	@RequestMapping("main.html")
	public String main(){
		return "main";
	}

	@RequestMapping("404.html")
	public String notFound(){
		return "404";
	}

}
