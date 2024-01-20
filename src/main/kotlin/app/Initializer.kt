package app

import config.Config
import config.SecurityConfig
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer

class Initializer : AbstractAnnotationConfigDispatcherServletInitializer() {
    override fun getRootConfigClasses(): Array<Class<*>> {
        return arrayOf(Config::class.java, SecurityConfig::class.java)
    }

    override fun getServletConfigClasses(): Array<Class<*>> {
        return arrayOf()
    }

    override fun getServletMappings(): Array<String> {
        return arrayOf("/api/*")
    }
}