/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.as.test.integration.weld.extensions.broken;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import javax.enterprise.inject.spi.Extension;

/**
 * bz961470
 *
 * Make sure CDI portable extensions with missing parent dependency do not fail to
 * deploy.
 *
 * A warning should be shown on deployment and the broken class not loaded, but
 * the deployment should succeed.
 *
 * @author rsmeral
 */
@RunWith(Arquillian.class)
public class ExtensionWithMissingParentDependencyTest {

    @Deployment
    public static Archive<?> deploy() {

        JavaArchive jar = ShrinkWrap.create(JavaArchive.class, "extension-with-missing-parent-dependency.jar");
        jar.add(new StringAsset(ExtensionWithMissingParentDependency.class.getName()), "META-INF/services/" + Extension.class.getName());
        jar.add(EmptyAsset.INSTANCE, "META-INF/beans.xml");
        jar.addClasses(ExtensionWithMissingParentDependency.class);// Foo.class intentionally omitted

        return jar;
    }

    @Test
    public void testArchiveDeploys() {
    }
}
