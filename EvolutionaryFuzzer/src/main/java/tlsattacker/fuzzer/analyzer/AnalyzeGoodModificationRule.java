/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2016 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package tlsattacker.fuzzer.analyzer;

import tlsattacker.fuzzer.config.analyzer.AnalyzeModificationRuleConfig;
import tlsattacker.fuzzer.config.EvolutionaryFuzzerConfig;
import tlsattacker.fuzzer.modification.Modification;
import tlsattacker.fuzzer.modification.ModificationType;
import tlsattacker.fuzzer.result.Result;
import de.rub.nds.tlsattacker.wrapper.MutableInt;
import java.io.File;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Logger;
import javax.xml.bind.JAXB;

/**
 * A Rule which counts the applied modifications which resulted in good TestVectors
 * 
 * @author Robert Merget - robert.merget@rub.de
 */
public class AnalyzeGoodModificationRule extends Rule {

    /**
     *
     */
    private static final Logger LOG = Logger.getLogger(AnalyzeGoodModificationRule.class.getName());

    /**
     *
     */
    private long executedTraces = 0;

    /**
     *
     */
    private HashMap<ModificationType, MutableInt> typeMap;

    /**
     *
     */
    private AnalyzeModificationRuleConfig config;

    /**
     *
     * @param evoConfig
     */
    public AnalyzeGoodModificationRule(EvolutionaryFuzzerConfig evoConfig) {
	super(evoConfig, "analyze_good_modification.rule");
	File f = new File(evoConfig.getAnalyzerConfigFolder() + configFileName);
	if (f.exists()) {
	    config = JAXB.unmarshal(f, AnalyzeModificationRuleConfig.class);
	}
	if (config == null) {
	    config = new AnalyzeModificationRuleConfig();
	    writeConfig(config);
	}
	typeMap = new HashMap<>();

    }

    /**
     *
     * @param result
     * @return
     */
    @Override
    public boolean applies(Result result) {
	return result.isGoodTrace() == Boolean.TRUE;
    }

    /**
     *
     * @param result
     */
    @Override
    public void onApply(Result result) {
	executedTraces++;
	for (Modification mod : result.getVector().getModificationList()) {
	    MutableInt i = typeMap.get(mod.getType());
	    if (i == null) {
		typeMap.put(mod.getType(), new MutableInt(1));
	    } else {
		i.addValue(1);
	    }
	}
    }

    /**
     *
     * @param result
     */
    @Override
    public void onDecline(Result result) {
    }

    /**
     *
     * @return
     */
    @Override
    public String report() {
	if (executedTraces > 0) {
	    StringBuilder b = new StringBuilder("Modifications which lead to good Traces:\n");
	    for (Entry<ModificationType, MutableInt> e : typeMap.entrySet()) {
		b.append(e.getKey().name() + " Count:" + e.getValue().getValue() + "\n");
	    }
	    return b.toString();
	} else {
	    return null;
	}
    }

    /**
     *
     * @return
     */
    public long getExecutedTraces() {
	return executedTraces;
    }

    /**
     *
     * @return
     */
    public HashMap<ModificationType, MutableInt> getTypeMap() {
	// TODO can we do sth like unmodifiable map?
	return typeMap;
    }

    /**
     *
     * @return
     */
    @Override
    public AnalyzeModificationRuleConfig getConfig() {
	return config;
    }


}
