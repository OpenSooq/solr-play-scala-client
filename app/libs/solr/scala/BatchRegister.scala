package libs.solr.scala

import org.apache.solr.client.solrj.SolrServer
import org.apache.solr.common.SolrInputDocument

class BatchRegister(server: SolrServer, docs: Map[String, Any]*){

  add(docs: _*)

  def add(docs: Any*): BatchRegister = {
    CaseClassMapper.toMapArray(docs: _*).foreach { doc =>
      val solrDoc = new SolrInputDocument
      doc.map { case (key, value) =>
        solrDoc.addField(key, value)
      }
      server.add(solrDoc)
    }
    this
  }

  def commit(): Unit = server.commit

  def rollback(): Unit = server.rollback

}