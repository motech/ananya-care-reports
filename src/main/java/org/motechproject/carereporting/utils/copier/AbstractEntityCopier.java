package org.motechproject.carereporting.utils.copier;

import org.apache.log4j.Logger;
import org.motechproject.carereporting.domain.AbstractEntity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public final class AbstractEntityCopier {

    private static final Logger LOG = Logger.getLogger(AbstractEntity.class);
    private static final int SIZE = 8192;

    private AbstractEntityCopier() {

    }

    public static AbstractEntity deepCopy(AbstractEntity entity) {
        AbstractEntity abstractEntity = null;

        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream(SIZE);
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(entity);
            out.flush();
            out.close();

            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
            abstractEntity = (AbstractEntity) in.readObject();
            abstractEntity.setId(null);
        } catch (IOException | ClassNotFoundException e) {
            LOG.error("", e);
        }

        return abstractEntity;
    }

}
