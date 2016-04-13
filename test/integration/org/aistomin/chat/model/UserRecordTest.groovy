package org.aistomin.chat.model

/**
 * Created by aistomin on 22/02/16.
 *
 * Test for {@link UserRecord}
 */
class UserRecordTest extends GroovyTestCase {

    /**
     * Check user's fields constraints.
     */
    void testConstraints() {
        final def test = 'test'
        shouldFail {
            new UserRecord(
                name: generate(21),
                username: test,
                password: test
            ).save()
        }
        shouldFail {
            new UserRecord(
                name: null,
                username: test,
                password: test
            ).save()
        }
        shouldFail {
            new UserRecord(
                name: '',
                username: test,
                password: test
            ).save()
        }
        shouldFail {
            new UserRecord(
                name: test,
                username: generate(16),
                password: test
            ).save()
        }
        shouldFail {
            new UserRecord(
                name: test,
                username: null,
                password: test
            ).save()
        }
        shouldFail {
            new UserRecord(
                name: test,
                username: '',
                password: test
            ).save()
        }
        new UserRecord(
            name: test,
            username: test,
            password: test
        ).save()
        assertNotNull(UserRecord.findByUsername(test))
    }

    /**
     * Generate string with given length.
     * @param length String length.
     * @return Generated string.
     */
    private static String generate(final int length) {
        final def res = new StringBuilder()
        length.times {
            res.append(String.valueOf(new Random().nextInt(10)))
        }
        return res.toString()
    }
}
