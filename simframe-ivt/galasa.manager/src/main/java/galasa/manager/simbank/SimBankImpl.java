package galasa.manager.simbank;

import java.lang.annotation.Annotation;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dev.galasa.ManagerException;
import dev.galasa.common.zos3270.internal.terminal.fields.Field;
import dev.galasa.framework.spi.AbstractManager;
import dev.galasa.framework.spi.AnnotatedField;
import dev.galasa.framework.spi.GenerateAnnotatedField;
import dev.galasa.framework.spi.IFramework;
import dev.galasa.framework.spi.IManager;
import dev.galasa.framework.spi.ResourceUnavailableException;

public class SimBankImpl extends AbstractManager implements ISimBank {
    
    private static final Log logger = LogFactory.getLog(SimBankImpl.class);

    @GenerateAnnotatedField(annotation = SimframeBank.class)
    public ISimBank fillfield(Field field, List<Annotation> annotations) {
        return this;
    }

    @Override
    public void provisionGenerate() throws ManagerException, ResourceUnavailableException {
		generateAnnotatedFields(SimBankManagerField.class);
    }
    
    @Override
	public void provisionStop() {
		
    }
    
    @Override
	public void initialise(@NotNull IFramework framework, @NotNull List<IManager> allManagers,
			@NotNull List<IManager> activeManagers, @NotNull Class<?> testClass) throws ManagerException {
		super.initialise(framework, allManagers, activeManagers, testClass);
		List<AnnotatedField> ourFields = findAnnotatedFields(SimBankManagerField.class);
		if (!ourFields.isEmpty()) {
			youAreRequired(allManagers, activeManagers);
		}
    }
    
    @Override
	public void youAreRequired(@NotNull List<IManager> allManagers, @NotNull List<IManager> activeManagers)
			throws ManagerException {
		if (activeManagers.contains(this)) {
			return;
		}

		activeManagers.add(this);
	}
}