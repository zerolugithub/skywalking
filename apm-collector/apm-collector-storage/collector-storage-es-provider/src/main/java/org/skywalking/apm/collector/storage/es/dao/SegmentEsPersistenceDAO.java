/*
 * Copyright 2017, OpenSkywalking Organization All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Project repository: https://github.com/OpenSkywalking/skywalking
 */

package org.skywalking.apm.collector.storage.es.dao;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.skywalking.apm.collector.client.elasticsearch.ElasticSearchClient;
import org.skywalking.apm.collector.storage.dao.ISegmentPersistenceDAO;
import org.skywalking.apm.collector.storage.es.base.dao.EsDAO;
import org.skywalking.apm.collector.storage.table.segment.Segment;
import org.skywalking.apm.collector.storage.table.segment.SegmentTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author peng-yongsheng
 */
public class SegmentEsPersistenceDAO extends EsDAO implements ISegmentPersistenceDAO<IndexRequestBuilder, UpdateRequestBuilder, Segment> {

    private final Logger logger = LoggerFactory.getLogger(SegmentEsPersistenceDAO.class);

    public SegmentEsPersistenceDAO(ElasticSearchClient client) {
        super(client);
    }

    @Override public Segment get(String id) {
        return null;
    }

    @Override public UpdateRequestBuilder prepareBatchUpdate(Segment data) {
        return null;
    }

    @Override public IndexRequestBuilder prepareBatchInsert(Segment data) {
        Map<String, Object> source = new HashMap<>();
        source.put(SegmentTable.COLUMN_DATA_BINARY, new String(Base64.getEncoder().encode(data.getDataBinary())));
        source.put(SegmentTable.COLUMN_TIME_BUCKET, data.getTimeBucket());
        logger.debug("segment source: {}", source.toString());
        return getClient().prepareIndex(SegmentTable.TABLE, data.getId()).setSource(source);
    }
}
