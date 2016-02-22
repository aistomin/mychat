package org.aistomin

/**
 * Created by aistomin on 22/02/16.
 *
 * Test for {@link User}
 */
class UserEntityTest extends GroovyTestCase {

    /**
     * Check user's fields constraints.
     */
    void testConstraints() {
        final def test = 'test'
        shouldFail {
            new User(
                name: generate(21),
                username: test,
                password: test
            ).save()
        }
        shouldFail {
            new User(
                name: null,
                username: test,
                password: test
            ).save()
        }
        shouldFail {
            new User(
                name: '',
                username: test,
                password: test
            ).save()
        }
        shouldFail {
            new User(
                name: test,
                username: generate(16),
                password: test
            ).save()
        }
        shouldFail {
            new User(
                name: test,
                username: null,
                password: test
            ).save()
        }
        shouldFail {
            new User(
                name: test,
                username: '',
                password: test
            ).save()
        }
        new User(
            name: test,
            username: test,
            password: test
        ).save()
        assertNotNull(User.findByUsername(test))
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
