
package com.github.uscexp.grappa.extension.parser.peg;

import java.util.logging.Logger;

import org.parboiled.Node;

import com.github.uscexp.grappa.extension.nodes.AstCommandTreeNode;


/**
 * Command implementation for the <code>PegParser</code> rule: OR.
 * 
 */
public class AstORTreeNode<V >
    extends AstCommandTreeNode<V>
{

	private static Logger logger = Logger.getLogger(AstORTreeNode.class.getName());

    public AstORTreeNode(Node<?> arg0, String arg1) {
        super(arg0, arg1);
    }

    protected void interpret(Long arg0)
        throws ReflectiveOperationException
    {
        logger.info("create firstOf()");
    }

}
