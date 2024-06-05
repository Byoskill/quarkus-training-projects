package app;

import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@OpenAPIDefinition(
    tags = {
            @Tag(name="adoption", description="Adoption operations."),
            @Tag(name="communication", description="Communication/marketing services")
    },
    info = @Info(
        title="Adoption/Communication API",
        version = "1.0.0",
        contact = @Contact(
            name = "Example API Support",
            url = "http://exampleurl.com/contact",
            email = "techsupport@example.com"),
        license = @License(
            name = "Propriétaire",
            url = "https://www.monsteradoption.com"))
)
public class ExampleApiApplication extends Application {
}









