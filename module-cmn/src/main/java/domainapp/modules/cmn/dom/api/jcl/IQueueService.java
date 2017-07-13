package domainapp.modules.cmn.dom.api.jcl;


import domainapp.modules.cmn.dom.api.IContext;

public interface IQueueService {

	void post(IQueue queue, IContext context) throws InterruptedException;
}
